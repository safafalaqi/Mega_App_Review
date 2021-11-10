package com.example.megaapp.adaptr

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.megaapp.HomeFragment
import com.example.megaapp.R
import com.example.megaapp.databinding.ItemRowBinding
import com.example.megaapp.model.User
import kotlinx.android.synthetic.main.delete_dialog.*
import kotlinx.android.synthetic.main.edit_dialog.*

class RVAdapter(val fragment: HomeFragment): RecyclerView.Adapter<RVAdapter.ItemViewHolder>() {
    class ItemViewHolder(val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root)

    var users = emptyList<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val user = users[position].name
        val location = users[position].location

        holder.binding.apply {
        tvName.text=user
        tvLocation.text= location
        }

        holder.binding.btUpdate.setOnClickListener{
           updateDialog(position)
        }

        holder.binding.btDelete.setOnClickListener{
            deleteDialog(position)
        }

    }

    fun updateDialog(position: Int){
        var myInfoDialog = Dialog(fragment.requireContext())
        myInfoDialog.setContentView(R.layout.edit_dialog)
        myInfoDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myInfoDialog.show()

        myInfoDialog.etNameUpdate.setText(users[position].name)
        myInfoDialog.etLocationUpdate.setText(users[position].location)

        myInfoDialog.btUpdateDialog.setOnClickListener {
            if (myInfoDialog.etNameUpdate.text.isNotBlank() && myInfoDialog.etLocationUpdate.text.isNotBlank()) {
                users[position].name = myInfoDialog.etNameUpdate.text.toString()
                users[position].location = myInfoDialog.etLocationUpdate.text.toString()
                fragment.updateForBoth(users[position])
                Toast.makeText(
                    fragment.requireContext(),
                    "Update success!!",
                    Toast.LENGTH_SHORT
                ).show()
                myInfoDialog.dismiss()
            }
            else {
                Toast.makeText(
                    fragment.requireContext(),
                    "Do not leave it empty",
                    Toast.LENGTH_SHORT
                ).show()
                myInfoDialog.dismiss()
            }
        }
        myInfoDialog.btCloseUpdate.setOnClickListener{
            myInfoDialog.dismiss()
        }


    }

    fun deleteDialog(position: Int){
        var myInfoDialog = Dialog(fragment.requireContext())
        myInfoDialog.setContentView(R.layout.delete_dialog)
        myInfoDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myInfoDialog.show()

        myInfoDialog.tvDelete.setText("${users[position].name}\n${users[position].location}")

        myInfoDialog.btDeleteDialog.setOnClickListener {
            fragment.deleteFromBoth(users[position])
            Toast.makeText(
                fragment.requireContext(),
                "Deleted successfully!!",
                Toast.LENGTH_SHORT
            ).show()
            myInfoDialog.dismiss()
        }

        myInfoDialog.btCloseDelete.setOnClickListener{
            myInfoDialog.dismiss()
        }
    }

    override fun getItemCount() = users.size


    fun update(userList: List<User>) {
        users = userList
        notifyDataSetChanged()
    }
}