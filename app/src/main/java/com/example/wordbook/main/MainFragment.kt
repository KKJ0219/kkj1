package com.example.wordbook.main

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import com.example.wordbook.BaseActivity
import com.example.wordbook.R
import com.example.wordbook.databinding.FragmentMainBinding
import com.example.wordbook.study.StudyDateFragment
import com.example.wordbook.study.StudyFragment
import com.example.wordbook.test.TestDateFragment
import com.example.wordbook.test.TestFragment
import com.example.wordbook.test.TestResultFragment
import com.example.wordbook.test.TestWordGoalFragment
import com.example.wordbook.vocalist.VocaListBaseFragment


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    /*private var testCount = 0 //TestWordGoalFragment로부터 전달받은 testCount 값을 저장할 변수

    fun setTestCount(count: Int){
        testCount = count //testCount 값을 설정
    }

    fun getTestCount(): Int{
        return testCount // testCount 값을 반환
    }*/


    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentMainBinding
    private lateinit var supportFragmentManager: FragmentManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_main, container, false)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.viewModel = viewModel



        viewModel.mStudyMovingState.observe(viewLifecycleOwner) {
            when (it) {
                MovingState.IDLE -> {}
                MovingState.MOVE -> {
                    addStudyFragment()
                    viewModel.setStudyMovingStateIdle()
                }
                MovingState.FAIL -> {
                    Toast.makeText(context, R.string.error_limit_1_word, Toast.LENGTH_SHORT).show()
                    viewModel.setStudyMovingStateIdle()
                }
            }
        }

        viewModel.mTestMovingState.observe(viewLifecycleOwner) {
            when (it) {
                MovingState.IDLE -> {}
                MovingState.MOVE -> {
                    addTestFragment()
                    viewModel.setTestMovingStateIdle()
                }
                MovingState.FAIL -> {
                    Toast.makeText(context, R.string.error_limit_4_word, Toast.LENGTH_SHORT).show()
                    viewModel.setTestMovingStateIdle()
                }
            }
        }


        binding.register.setOnClickListener {
            addVocaListFragment()
        }


        //추가한 부분
        //매일학습테스트 버튼 클릭
        binding.test.setOnClickListener{
            addTestDateFragment()
        }

        //단어목표설정 버튼 클릭
        binding.testwordgoal.setOnClickListener{
            addTestWordGoalFragment()
        }

        binding.study.setOnClickListener{
            addStudyDateFragment()
        }

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        supportFragmentManager = requireActivity().supportFragmentManager

    }

    private fun addVocaListFragment() {
        supportFragmentManager
            .beginTransaction()
            .setReorderingAllowed(true)
            .replace(BaseActivity.FRAGMENT_CONTAINER_ID, VocaListBaseFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    private fun addTestFragment() {
        supportFragmentManager
            .beginTransaction()
            .setReorderingAllowed(true)
            .replace(BaseActivity.FRAGMENT_CONTAINER_ID, TestFragment.newInstance())
            .addToBackStack(null)
            .commit()

    }

    private fun addStudyFragment() {
        supportFragmentManager
            .beginTransaction()
            .setReorderingAllowed(true)
            .replace(BaseActivity.FRAGMENT_CONTAINER_ID, StudyFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }


    //추가한 부분
    //메인페이지에서 "테스트" 버튼 클릭 시 testdate(테스트 날짜 선택)페이지로 이동
    private fun addTestDateFragment(){
        supportFragmentManager
            .beginTransaction()
            .setReorderingAllowed(true)
            .replace(BaseActivity.FRAGMENT_CONTAINER_ID, TestDateFragment.newInstance())
            .addToBackStack("MainFragment") //TestResultFragment에서 바로 여기로 돌아오게 name 설정함
            .commit()
    }


    //메인페이지에서 "단어목표설정" 버튼 클릭 시 testwordgoal(단어목표설정)페이지로 이동
    private fun addTestWordGoalFragment(){
        supportFragmentManager
            .beginTransaction()
            .setReorderingAllowed(true)
            .replace(BaseActivity.FRAGMENT_CONTAINER_ID, TestWordGoalFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }




    private fun addStudyDateFragment(){
        supportFragmentManager
            .beginTransaction()
            .setReorderingAllowed(true)
            .replace(BaseActivity.FRAGMENT_CONTAINER_ID, StudyDateFragment.newInstance())
            .addToBackStack("MainFragment")
            .commit()
    }


}


