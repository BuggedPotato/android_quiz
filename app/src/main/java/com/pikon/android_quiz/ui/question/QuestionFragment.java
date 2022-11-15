package com.pikon.android_quiz.ui.question;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.pikon.android_quiz.Quiz;
import com.pikon.android_quiz.R;
import com.pikon.android_quiz.databinding.FragmentGalleryBinding;
import com.pikon.android_quiz.databinding.FragmentQuestionBinding;
import com.pikon.android_quiz.ui.home.HomeFragment;
import com.pikon.android_quiz.ui.home.HomeViewModel;

public class QuestionFragment extends Fragment {

    private FragmentQuestionBinding binding;
    private Quiz quiz;

    public View onCreateView( @NonNull LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState ) {
        QuestionViewModel questionViewModel =
                new ViewModelProvider( this ).get( QuestionViewModel.class );
        HomeViewModel homeViewModel = new ViewModelProvider( this ).get( HomeViewModel.class );
        homeViewModel.getQuiz().observe(getViewLifecycleOwner(), new Observer<Quiz>() {
            @Override
            public void onChanged( Quiz q ) {
                quiz = q;
            }
        });

        binding = FragmentQuestionBinding.inflate( inflater, container, false );
        View root = binding.getRoot();

        final TextView tvTitle = binding.tvTitle;
        final LinearLayout llAnswers = binding.llAnswers;


        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}