package com.pikon.android_quiz;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class QuestionsRecyclerAdapter extends RecyclerView.Adapter<QuestionsRecyclerAdapter.ViewHolder> {

    private Quiz quiz;
    public static final HashMap<AnswerResult, AbstractMap.SimpleEntry<Integer, Integer>> coloursDrawables = new HashMap<AnswerResult, AbstractMap.SimpleEntry<Integer, Integer>>() {{
        put( AnswerResult.CORRECT, new AbstractMap.SimpleEntry( R.color.green_50, R.drawable.check_mark ) );
        put( AnswerResult.INCORRECT, new AbstractMap.SimpleEntry( R.color.red_50, R.drawable.cross_mark ) );
        put( AnswerResult.MIXED, new AbstractMap.SimpleEntry( R.color.yellow_d_50, R.drawable.tilde ) );
    }};
    public static final HashMap<AnswerResult, AbstractMap.SimpleEntry<Integer, Integer>> coloursBright = new HashMap<AnswerResult, AbstractMap.SimpleEntry<Integer, Integer>>() {{
        put( AnswerResult.CORRECT, new AbstractMap.SimpleEntry( R.color.green, R.drawable.check_mark ) );
        put( AnswerResult.INCORRECT, new AbstractMap.SimpleEntry( R.color.red, R.drawable.cross_mark ) );
        put( AnswerResult.MIXED, new AbstractMap.SimpleEntry( R.color.yellow_d, R.drawable.tilde ) );
    }};

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTitle;
        private final ImageView ivAnswerResult;
        private final LinearLayout llAnswers;

        public ViewHolder( View view ) {
            super( view );
            tvTitle = (TextView) view.findViewById( R.id.tvTitle );
            ivAnswerResult = (ImageView) view.findViewById( R.id.ivAnswerResult );
            llAnswers = (LinearLayout) view.findViewById( R.id.llAnswers );
        }

        public TextView getTvTitle() {
            return tvTitle;
        }

        public ImageView getIvAnswerResult() {
            return ivAnswerResult;
        }

        public LinearLayout getLlAnswers() {
            return llAnswers;
        }
    }

    public QuestionsRecyclerAdapter( Quiz quiz ) {
        this.quiz = quiz;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
        View view = LayoutInflater.from( parent.getContext() )
                .inflate( R.layout.question_recycler_card, parent, false );
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder( @NonNull ViewHolder holder, int position ) {
        Question question = quiz.getQuestions().get( position );
        holder.getTvTitle().setText( question.getText() );
        if ( holder.getLlAnswers().getChildCount() > 0 )
            holder.getLlAnswers().removeAllViews();

        for ( Answer a : question.getAnswers() ) {
            CheckBox checkBox = new CheckBox( holder.getTvTitle().getContext() );
            checkBox.setText( a.getText() );
            checkBox.setLayoutParams( new LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT ) );
            checkBox.setChecked( a.isAnswered() );

            Drawable drawable = AppCompatResources.getDrawable( holder.getTvTitle().getContext(), coloursDrawables.get( question.getResult() ).getValue() );
            holder.getIvAnswerResult().setImageDrawable( drawable );

            if ( a.isCorrect() )
                checkBox.setBackgroundColor( holder.getTvTitle().getContext().getColor( coloursDrawables.get( AnswerResult.CORRECT ).getKey() ) );
            else if ( !a.isCorrect() && a.isAnswered() )
                checkBox.setBackgroundColor( holder.getTvTitle().getContext().getColor( coloursDrawables.get( AnswerResult.INCORRECT ).getKey() ) );
            checkBox.setClickable( false );
            holder.getLlAnswers().addView( checkBox );
        }
    }

    @Override
    public int getItemCount() {
        return quiz.getQuestions().size();
    }
}
