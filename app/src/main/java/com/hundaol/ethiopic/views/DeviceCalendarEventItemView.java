package com.hundaol.ethiopic.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hundaol.ethiocal.R;
import com.hundaol.ethiopic.viewmodels.DeviceCalendarEventViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by abinet on 6/6/17.
 */

public class DeviceCalendarEventItemView extends LinearLayout {


    public DeviceCalendarEventItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @BindView(R.id.indicator)
    ImageView indicatorImage;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.time)
    TextView time;

    DeviceCalendarEventViewModel viewModel;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    public void bindTo(DeviceCalendarEventViewModel viewModel) {
        this.viewModel = viewModel;
        validateView();
        requestLayout();
    }

    private void validateView(){
        if(viewModel == null) {
            return;
        }

        indicatorImage.setBackground(viewModel.getBackgroundIndicator());
        title.setText(viewModel.getDeviceCalendar().getTitle());
        time.setText(viewModel.getDeviceCalendar().getStart() + "-" + viewModel.getDeviceCalendar().getEnd());
    }
}
