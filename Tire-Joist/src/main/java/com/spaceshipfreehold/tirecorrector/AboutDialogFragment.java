package com.spaceshipfreehold.tirecorrector;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AboutDialogFragment extends DialogFragment {

    private View mRoot;
    private TextView mAboutTextView;
    private ClickableSpan mEmailSpan;
    private ClickableSpan mContactSpan;

    public AboutDialogFragment(){ }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.getDialog().setTitle(getResources().getString(R.string.about));

        mRoot = inflater.inflate(R.layout.about_layout, container, false);
        mAboutTextView = mRoot.findViewById(R.id.about_text_view);
        mAboutTextView.setMovementMethod(LinkMovementMethod.getInstance());

        // Build about text in supported languages.
        String hereText = getResources().getString(R.string.here_text);
        String sourceCodeStatement = getResources().getString(R.string.source_code_location) + " " + hereText + ". ";
        int sourceLinkStart = startOfSubstring(sourceCodeStatement, hereText);
        int sourceLinkEnd = endOfSubstring(sourceCodeStatement, hereText) + 1;
        String contactMeStatement = getResources().getString(R.string.contact_me) + " " + hereText + ".";
        int contactLinkStart = sourceCodeStatement.length() + startOfSubstring(contactMeStatement, hereText);
        int contactLinkEnd = sourceCodeStatement.length() + endOfSubstring(contactMeStatement, hereText) + 1;

        // Create a color span for the links.
        ForegroundColorSpan blueSpan = new ForegroundColorSpan(Color.BLUE);

        // Make spannable with links in the proper locations for the text.
        SpannableString mastHeadText = new SpannableString( sourceCodeStatement + contactMeStatement);
        mastHeadText.setSpan(new SourceCodeIntentSpan(), sourceLinkStart, sourceLinkEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mastHeadText.setSpan(blueSpan, sourceLinkStart, sourceLinkEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mastHeadText.setSpan(new EmailIntentSpan(), contactLinkStart, contactLinkEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mastHeadText.setSpan(blueSpan, contactLinkStart, contactLinkEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //Set text into view.
        mAboutTextView.setText(mastHeadText);

        return mRoot;
    }

    private class SourceCodeIntentSpan extends ClickableSpan{
        @Override
        public void onClick(@NonNull View widget) {
            Intent websiteIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/esotericabsurdist/tires"));
            startActivity(Intent.createChooser(websiteIntent, getResources().getString(R.string.choose_browser_intent_title)));
        }
    }

    private class EmailIntentSpan extends ClickableSpan{
        @Override
        public void onClick(@NonNull View widget) {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto","spaceshipfreehold@protonmail.ch", null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Tire Joist");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "...");
            startActivity(Intent.createChooser(emailIntent, getResources().getString(R.string.send_email_intent_chooser_title)));
        }
    }


    /**
     * Returns the starting index of the first occurrence of the substring if it exists; returns -1
     * otherwise;
     * @param string
     * @param substring
     * @return
     */
    int startOfSubstring(String string, String substring){
        int substringLength = substring.length();
        int pointer = 0;
        int start = -1;
        for(int c = 0; c < string.length(); c++){
            if(pointer < substringLength){
                if(string.charAt(c) == substring.charAt(pointer) && pointer == 0){
                  start = c;
                  pointer ++;
                } else if(string.charAt(c) == substring.charAt(pointer)){
                    pointer++;
                } else {
                    start = -1;
                    pointer = 0;
                }
            } else if(pointer >= substringLength && start != -1){
                return start;
            }
        }
        return start;
    }

    /**
     * Returns the end of the first occurrence of the substring if it exists; -1 is return if not found.
     * @param string
     * @param substring
     * @return
     */
    int endOfSubstring(String string, String substring){
        int substringLength = substring.length();
        int pointer = 0;
        int end = -1;
        for(int c = 0; c < string.length(); c++){
            if(pointer < substringLength) {
                if (string.charAt(c) == substring.charAt(pointer) && pointer == substringLength-1) {
                    end = c;
                    break;
                } else if (string.charAt(c) == substring.charAt(pointer)) {
                    pointer++;
                } else {
                    pointer = 0;
                }
            }
        }
        return end;
    }
}
