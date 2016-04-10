/**
 * 
 */
package com.test.messagefilterapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.messagefilterapplication.R;

/**
 * @author Amey Gadgil
 *
 */
public class MessagingFragment extends Fragment {
	private Button mFilterNowButton, mClearMessagesButton;
	private LinearLayout mMessageContainer;
	private EditText mInput;
	
	
	private View.OnClickListener mClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			int id = v.getId();
			if(id==R.id.filter_button){
				processMessage();
			}else if(id==R.id.clear_button){
				mMessageContainer.removeAllViews();
			}
			
		}
	};

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.message_view, container,
				false);
		
		mFilterNowButton = (Button)rootView.findViewById(R.id.filter_button);
		mFilterNowButton.setOnClickListener(mClickListener);
		mClearMessagesButton = (Button)rootView.findViewById(R.id.clear_button);
		mClearMessagesButton.setOnClickListener(mClickListener);
		
		mInput = (EditText)rootView.findViewById(R.id.input_area);
		
		mMessageContainer = (LinearLayout)rootView.findViewById(R.id.message_container);
		return rootView;
	}
	
	private void processMessage(){
		if(TextUtils.isEmpty(mInput.getText())){
			Toast.makeText(getActivity().getApplicationContext(), R.string.empty_input, Toast.LENGTH_SHORT).show();
			return;
		}
		String input = mInput.getText().toString().replaceAll("[\n\r]", " ");
		String jsonString = TextMessageProcessor.getInstance(getActivity()).getContent(input);
		if(TextUtils.isEmpty(jsonString)){
			Toast.makeText(getActivity().getApplicationContext(), R.string.no_content_filtered, Toast.LENGTH_LONG).show();
			return;
		}
		
		TextView message = (TextView)getActivity().getLayoutInflater().inflate(R.layout.message_item, null);
		mInput.setText("");
		message.setText(jsonString);
		mMessageContainer.addView(message);
		
	}

}
