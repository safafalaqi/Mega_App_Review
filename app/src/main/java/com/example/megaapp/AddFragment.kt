package com.example.megaapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.megaapp.api.APIClient
import com.example.megaapp.api.APIInterface
import com.example.megaapp.model.User
import kotlinx.android.synthetic.main.fragment_add.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddFragment : Fragment() {
    lateinit var myViewModel: UserModelView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_add, container, false)

        myViewModel = ViewModelProvider(requireActivity()).get(UserModelView::class.java)

        view.btAddDialog.setOnClickListener{
            if(view.etNameAdd.text.isNotBlank() && view.etLocationAdd.text.isNotBlank()) {
                addToAPi(User(null,view.etNameAdd.text.toString(),view.etLocationAdd.text.toString()))
                Toast.makeText(requireContext(), "Added successfully!!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_DBFragment_to_homeFragment)
            }
            else
             Toast.makeText(requireContext(), "Something went wrong!", Toast.LENGTH_SHORT).show()
        }
        view.btCloseAdd.setOnClickListener{
            findNavController().navigate(R.id.action_DBFragment_to_homeFragment)
        }

        // Inflate the layout for this fragment
        return view
    }


    fun addToAPi(user: User){
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        apiInterface!!.addUsersInfo(user).enqueue(object :
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
}