package com.pikon.android_quiz.ui.result;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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

import com.pikon.android_quiz.AnswerResult;
import com.pikon.android_quiz.QuestionsRecyclerAdapter;
import com.pikon.android_quiz.Quiz;
import com.pikon.android_quiz.R;
import com.pikon.android_quiz.databinding.FragmentResultBinding;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
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

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController( view )
                        .navigate( R.id.action_nav_result_to_nav_home );
            }
        });

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
        resultViewModel.setQuiz( (Quiz) getArguments().getSerializable( "quiz" ) );

        Toolbar toolbar = (Toolbar) ( (AppCompatActivity) getActivity()).findViewById( R.id.toolbar );
        toolbar.setNavigationOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                Navigation.findNavController( view )
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
        float correct = quiz.getScore();
        float total =  quiz.getTotalScore();
        long seconds = Duration.between( (Instant)getArguments().getSerializable( "startTime" ), (Instant)getArguments().getSerializable( "finishTime" ) ).getSeconds();
        String score = String.format( Locale.ROOT, "%.2f/%.2f", correct, total );
        String percentage = String.format( Locale.ROOT, "(%.1f%%)", correct/total * 100 );
        binding.tvTime.setText(String.format( Locale.ROOT, "Finished in %d seconds!", seconds) );

        binding.tvScore.setText( score );
        binding.tvPercentage.setText( percentage );

        binding.pbScoreBar.setMax( (int)(total * 100) );
        binding.pbScoreBar.setProgress( (int)(correct * 100), true );

        binding.llScoreBars.setClipToOutline(true);
        binding.llScoreBars.addView( getScoreBar( AnswerResult.CORRECT, quiz.getAnsweredOfResultCount( AnswerResult.CORRECT ), quiz.getQuestions().size() ) );
        binding.llScoreBars.addView( getScoreBar( AnswerResult.MIXED, quiz.getAnsweredOfResultCount( AnswerResult.MIXED ), quiz.getQuestions().size() ) );
        binding.llScoreBars.addView( getScoreBar( AnswerResult.INCORRECT, quiz.getAnsweredOfResultCount( AnswerResult.INCORRECT ), quiz.getQuestions().size() ) );
    }

    private View getScoreBar(AnswerResult result, int count, int max){
        View view = new View( getContext() );
        view.setLayoutParams( new LinearLayout.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, max - count ) );
        view.setBackgroundColor( getContext().getColor( QuestionsRecyclerAdapter.coloursBright.get( result ).getKey() ) );
        return view;
    }
}
