package com.pikon.android_quiz.ui.result;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pikon.android_quiz.QuestionsRecyclerAdapter;
import com.pikon.android_quiz.Quiz;
import com.pikon.android_quiz.R;
import com.pikon.android_quiz.databinding.FragmentResultBinding;

import java.util.Locale;

public class ResultFragment extends Fragment {

    private FragmentResultBinding binding;
    private ResultViewModel resultViewModel;
    private QuestionsRecyclerAdapter adapter;

    public View onCreateView( @NonNull LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState ) {

        binding = FragmentResultBinding.inflate( inflater, container, false );
        View root = binding.getRoot();

        getActivity().setTitle( "Quiz results" );

        RecyclerView recycler = binding.rvRecycler;
        recycler.setLayoutManager( new LinearLayoutManager( getContext() ) );
        if( adapter != null )
            recycler.setAdapter( adapter );

        resultViewModel = new ViewModelProvider( this ).get( ResultViewModel.class );
        resultViewModel.getQuiz().observe( getViewLifecycleOwner(), new Observer<Quiz>() {
            @Override
            public void onChanged( Quiz quiz ) {
                if( quiz != null ){
                    adapter = new QuestionsRecyclerAdapter( quiz );
                    recycler.setAdapter( adapter );
                    showQuizResults( quiz );
                }
            }
        } );
        Log.d( "DEBUG", ((Quiz) getArguments().getSerializable( "quiz" )).toString() );
        resultViewModel.setQuiz( (Quiz) getArguments().getSerializable( "quiz" ) );

        Fragment selfRef = this;
        Toolbar toolbar = (Toolbar) ( (AppCompatActivity) getActivity()).findViewById( R.id.toolbar );
        toolbar.setNavigationOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                NavHostFragment.findNavController( selfRef )
                        .navigate( R.id.action_nav_result_to_nav_home );
            }
        } );

        requireActivity().getOnBackPressedDispatcher().addCallback( new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController( root ).navigate( R.id.action_nav_result_to_nav_home );
            }
        } );

        return root;
    }

    private void showQuizResults( Quiz quiz ) {
        int correct = quiz.getCorrectAnswersCount();
        int total =  quiz.getQuestions().size();
        String score = String.format( Locale.ROOT, "%d/%d", correct, total, (float) correct/total * 100 );
        String percentage = String.format( Locale.ROOT, "(%.1f%%)", (float) correct/total * 100 );
        binding.tvScore.setText( score );
        binding.tvPercentage.setText( percentage );

        binding.pbScoreBar.setMax( total );
        binding.pbScoreBar.setProgress( correct, true );
    }
}
