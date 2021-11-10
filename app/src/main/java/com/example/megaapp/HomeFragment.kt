package com.example.megaapp

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.megaapp.adaptr.RVAdapter
import com.example.megaapp.api.APIClient
import com.example.megaapp.api.APIInterface
import com.example.megaapp.model.User
import com.example.megaapp.model.Users
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {
    private lateinit var rvAdapter: RVAdapter
    lateinit var myViewModel: UserModelView
    var users= Users()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        myViewModel = ViewModelProvider(requireActivity()).get(UserModelView::class.java)

        rvAdapter = RVAdapter(this)
        val myRV = view.findViewById<RecyclerView>(R.id.rvList)
        myRV.adapter = rvAdapter
        myRV.layoutManager = LinearLayoutManager(this.context)

        myViewModel.getUsers().observe(viewLifecycleOwner, {list->
            list?.let { rvAdapter.update(list) }
        })

        getUsersFromApi()

        view.addFloatingButton.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_DBFragment)
        }

        view.updateFloatingButton.setOnClickListener{
            getUsersFromApi()
        }

        return view
    }

    private fun setRV() {
            rvAdapter = RVAdapter(this)
            rvList.adapter = rvAdapter
            rvList.layoutManager = LinearLayoutManager(this.context)
    }

    fun getUsersFromApi() {

        //show progress Dialog
        val progressDialog = ProgressDialog(this.context)
        progressDialog.setMessage("Please wait")
        progressDialog.show()

        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        val call: Call<Users?>? = apiInterface!!.getUsersInfo()

        call?.enqueue(object : Callback<Users?> {
            override fun onResponse(
                call: Call<Users?>?,
                response: Response<Users?>
            ) {
                progressDialog.dismiss()

                rvAdapter.update(response.body()!!)

                users.clear()
                users=response.body()!!

                var allDBUsers = myViewModel.getUsers().value
                //add all users to the database
                users.forEach {
                    if (allDBUsers != null) {
                        if(it !in allDBUsers){
                            myViewModel.addUser(it)
                        }
                    }else{
                        myViewModel.addUser(it)
                    }

                }


            }
            override fun onFailure(call: Call<Users?>, t: Throwable?) {
                Toast.makeText(requireContext(),"Unable to load data!", Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()
                call.cancel()
            }
        })
    }

    fun updateForBoth(user: User){
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        apiInterface!!.updateUsersInfo(user.pk!!,user).enqueue(object :
            Callback<User?> {
            override fun onResponse(
                call: Call<User?>?,
                response: Response<User?>
            ) {
                Toast.makeText(requireContext(), "Update Success!", Toast.LENGTH_SHORT).show()
            }
            override fun onFailure(call: Call<User?>, t: Throwable) {
                Toast.makeText(requireContext(), "Unable to update user", Toast.LENGTH_SHORT)
                    .show()
                call.cancel()
            }
        })
        myViewModel.editUser(user)

    }

    fun deleteFromBoth(user:User){
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        apiInterface!!.deleteUsersInfo(user.pk!!).enqueue(object : Callback<Void> {
            override fun onResponse(
                call: Call<Void>?,
                response: Response<Void>
            ) {
                Toast.makeText(requireContext(), "Delete Success!", Toast.LENGTH_SHORT)
                    .show()
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(requireContext(), "Unable to delete user", Toast.LENGTH_SHORT).show()
                call.cancel() }
        })
        myViewModel.deleteUser(user)
    }

}