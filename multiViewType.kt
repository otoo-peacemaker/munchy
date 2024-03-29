package com.infoviewsims.school_msis_20.ui.main.student_classroom

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.infoviewsims.school_msis_20.databinding.StudentListBinding
import com.infoviewsims.school_msis_20.databinding.StudentListWithoutCheckboxBinding
import com.infoviewsims.school_msis_20.ui.data.StudentData
import com.infoviewsims.school_msis_20.ui.main.student_classroom.ClassroomAdapter.Const.HASCHKBOX
import com.infoviewsims.school_msis_20.ui.main.student_classroom.ClassroomAdapter.Const.NOCHKBOX

class ClassroomAdapter(
    private var studentData: List<StudentData>,
    private var itemCheckListener: (isChecked: Boolean, data: MutableList<StudentInfo>) -> Unit,
    ) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val data = mutableListOf<StudentInfo>()

    override fun getItemCount(): Int = studentData.size

    //bind the recycler list items
    inner class StudentListViewHolder(val binding: StudentListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(data: StudentData?) {
            binding.tvname.text = data?.Frist_Name?.plus(" ${data.Last_Name}")
            binding.tvAge.text = "Age"
            binding.tvGender.text = data?.Gender
        }
    }

    inner class StudentListWithoutCheckboxVH(private val binding: StudentListWithoutCheckboxBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: StudentData) {
            binding.tvname.text = data.Frist_Name.plus(" ${data.Last_Name}")
            binding.tvAge.text = "Age"
            binding.tvGender.text = data.Gender
        }
    }

    //inflate the List

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == HASCHKBOX) {
            val view =
                StudentListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            StudentListViewHolder(view)
        } else {
            val view =
                StudentListWithoutCheckboxBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            StudentListWithoutCheckboxVH(view)
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == HASCHKBOX){
            (holder as StudentListViewHolder).bind(studentData[position])
            holder.binding.isChecked.setOnCheckedChangeListener{_,isChecked ->
                if (isChecked) {
                    data.add(
                        StudentInfo(
                            firstName = studentData[position].Frist_Name,
                            lastName = studentData[position].Last_Name,
                            gender = studentData[position].Gender,
                        )
                    )
                    itemCheckListener(true, data)
                    holder.binding.llh1.setBackgroundColor(Color.WHITE)
                }else{
                    itemCheckListener(false, data)
                    data.remove(
                        StudentInfo(
                            firstName = studentData[position].Frist_Name,
                            lastName = studentData[position].Last_Name,
                            gender = studentData[position].Gender
                        )
                    )
                    holder.binding.llh1.setBackgroundColor(Color.parseColor("#ECECEC"))
                }
            }

        } else{
            (holder as StudentListWithoutCheckboxVH).bind(studentData[position])

        }
    }

    //bind the model list to the recycler list
    override fun getItemViewType(position: Int): Int {
        return if (studentData[position].hasCheckbox == HasCheckbox.TRUE) HASCHKBOX else NOCHKBOX
    }

    //perform update operation
    class ListComparator : DiffUtil.ItemCallback<StudentData>() {
        override fun areItemsTheSame(oldItem: StudentData, newItem: StudentData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: StudentData, newItem: StudentData): Boolean {
            return oldItem == newItem
        }
    }


    interface OnHideView {
        fun onHideView(view: View)
    }

    private object Const{
        const val HASCHKBOX = 0 // random unique value
        const val NOCHKBOX = 1
    }

}
data class StudentInfo(
    val firstName: String,
    val lastName: String,
    val age: String? = null,
    val gender: String,
    val hasCheckbox: HasCheckbox?=null
)
enum class HasCheckbox {
    TRUE, FALSE
}
  /* val recyclerView = binding.studentRecycler
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = ClassroomAdapter(stdList.getStudentList()) { _, data ->
                Log.d("CLICKS", "YOU CLICK  FRAGMENT \n $data")
            }
        }*/
//ListAdapter<StudentData, ClassroomAdapter.StudentListViewHolder>(ListComparator())
//https://www.section.io/engineering-education/implementing-multiple-viewholders-in-android-using-kotlin/
