/**
 * 
 */
package com.test.messagefilterapplication;

import java.util.ArrayList;
import java.util.List;

import com.example.messagefilterapplication.R;
import com.test.messagefilterapplication.TextMessageProcessor.ListStateHolder;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;

/**
 * @author Amey Gadgil
 *
 */
public class SettingsFragment extends Fragment {
	ListView mListView;
	CheckBox mSelectAll;
	boolean isSelectAllChecked;
	LayoutInflater mInflater;
	FilterListAdapter mAdapter;

	ArrayList<ListStateHolder> mFilterList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View rootView = inflater.inflate(R.layout.settings_fragment, container,
				false);
		mListView = (ListView) rootView.findViewById(R.id.filter_list);
		mSelectAll = (CheckBox) rootView.findViewById(R.id.select_all);
		// mSelectAll.setOnCheckedChangeListener(mCheckChangeListener);
		mSelectAll.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				CheckBox b = (CheckBox) v;
				boolean isChecked = b.isChecked();
				for (ListStateHolder holder : mFilterList) {
					holder.isChecked = isChecked;
				}
				mAdapter.notifyDataSetInvalidated();

				// TODO Auto-generated method stub

			}
		});

		mFilterList = TextMessageProcessor.getInstance(getActivity().getApplicationContext()).getAvailableFilterList();
		
		mAdapter = new FilterListAdapter(getActivity().getApplicationContext(),
				R.layout.filter_list_item, mFilterList);
		mListView.setAdapter(mAdapter);

		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (((CheckBox) view).isChecked()) {
					mFilterList.get(position).isChecked = true;
				} else {
					mFilterList.get(position).isChecked = false;
				}

			}

		});

		return rootView;
	}

	

	class FilterListAdapter extends ArrayAdapter<ListStateHolder> {

		public FilterListAdapter(Context context, int resource,
				List<ListStateHolder> objects) {
			super(context, resource, objects);
		}
		
		private OnCheckedChangeListener mCheckChangeListener = new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (buttonView.getId() == R.id.list_item) {
					getItem(((Integer)buttonView.getTag()).intValue()).isChecked = isChecked;

					if (!isChecked && mSelectAll.isChecked()) {
						mSelectAll.setChecked(false);
					}
				}

			}

		};

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			if (null == convertView) {
				convertView = mInflater
						.inflate(R.layout.filter_list_item, null);
				((CheckBox) convertView.findViewById(R.id.list_item))
						.setOnCheckedChangeListener(mCheckChangeListener);

			}
			updateItem(((CheckBox) convertView.findViewById(R.id.list_item)),
					position);
			return convertView;
		}

		private void updateItem(CheckBox checkbox, int position) {
			checkbox.setTag(position);
			checkbox.setText(getItem(position).mLabel);
			checkbox.setChecked(getItem(position).isChecked);
		}
	}
	
	@Override
	public void onDestroy() {
		TextMessageProcessor.getInstance(getActivity()).updateFilters();
//		mAdapter.clear();	// dont clear.  till We pass  cloned arraylist reference from MessageProcessor
		mAdapter = null;
		mListView = null;
//		mFilterList.clear();	
		super.onDestroy();
	}
}
