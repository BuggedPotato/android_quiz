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

import java.util.HashMap;

public class QuestionsRecyclerAdapter extends RecyclerView.Adapter<QuestionsRecyclerAdapter.ViewHolder> {

    private Quiz quiz;

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

        final HashMap<AnswerResult, Integer> dimmedColours = new HashMap<AnswerResult, Integer>() {{
            put( AnswerResult.CORRECT, R.color.green_50 );
            put( AnswerResult.INCORRECT, R.color.red_50 );
            put( AnswerResult.MIXED, R.color.yellow_d_50 );
        }};

        for ( Answer a : question.getAnswers() ) {
            CheckBox checkBox = new CheckBox( holder.getTvTitle().getContext() );
            checkBox.setText( a.getText() );
            checkBox.setLayoutParams( new LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT ) );
            checkBox.setChecked( a.isAnswered() );

            Drawable drawable = AppCompatResources.getDrawable( holder.getTvTitle().getContext(), question.getResult() == AnswerResult.CORRECT ? R.drawable.check_mark : R.drawable.cross_mark );
            holder.getIvAnswerResult().setImageDrawable( drawable );

            if ( a.isCorrect() )
                checkBox.setBackgroundColor( holder.getTvTitle().getContext().getColor( dimmedColours.get( AnswerResult.CORRECT ) ) );
            else if ( !a.isCorrect() && a.isAnswered() )
                checkBox.setBackgroundColor( holder.getTvTitle().getContext().getColor( dimmedColours.get( AnswerResult.INCORRECT ) ) );
            checkBox.setClickable( false );
            holder.getLlAnswers().addView( checkBox );
        }
    }

    @Override
    public int getItemCount() {
        return quiz.getQuestions().size();
    }
}
