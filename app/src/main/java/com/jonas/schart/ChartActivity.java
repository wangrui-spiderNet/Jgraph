package com.jonas.schart;

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.jonas.jgraph.graph.JcoolGraph;
import com.jonas.jgraph.models.Jchart;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import static com.jonas.jgraph.graph.JcoolGraph.LINE_DASH_0;
import static com.jonas.jgraph.inter.BaseGraph.SELECETD_MSG_SHOW_TOP;
import static com.jonas.jgraph.inter.BaseGraph.SELECETD_NULL;

public class ChartActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private JcoolGraph mLineChar;
    private String linestyleItems[] = new String[]{"折线", "曲线"};
    //柱状图动画样式
    private String showstyleItems[] = new String[]{"DRAWING", "SECTION", "FROMLINE", "FROMCORNER", "ASWAVE"};
    //折线图动画样式
    private String barshowstyleItems[] = new String[]{"ASWAVE", "FROMLINE", "EXPAND", "SECTION"};
    private int chartNum = 10;

    private void logData(String msg) {
        Log.e("图表数据", "数据：" + msg);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        setUpListview();
        setupCheckBox();
        mLineChar = (JcoolGraph) findViewById(R.id.sug_recode_line);

        List<Jchart> lines = new ArrayList<>();
        for (int i = 0; i < chartNum; i++) {

//            lines.add(new Jchart(new SecureRandom().nextInt(50)+15, Color.parseColor("#5F77F6")));

            int lower = new SecureRandom().nextInt(50);
            int upper = new SecureRandom().nextInt(50) + 15;
            logData("upper:" + upper + " lower:" + lower);

            lines.add(new Jchart(lower, upper, "名:"+i, Color.parseColor("#ff0000")));
        }
//        for(Jchart line : lines) {
//            line.setStandedHeight(100);
//        }
        //        lines.get(new SecureRandom().nextInt(chartNum-1)).setUpper(0);
//                lines.get(1).setUpper(0);
//                lines.get(new SecureRandom().nextInt(chartNum-1)).setLower(10);
//                lines.get(chartNum-2).setUpper(0);
        //        mLineChar.setScrollAble(true);
//        mLineChar.setLineMode();
        mLineChar.setLinePointRadio((int) mLineChar.getLineWidth());
//        mLineChar.setLineMode(JcoolGraph.LineMode.LINE_DASH_0);
//        mLineChar.setLineStyle(JcoolGraph.LineStyle.LINE_BROKEN);

//                mLineChar.setYaxisValues("test","测试","text");
        //        mLineChar.setSelectedMode(BaseGraph.SelectedMode.SELECETD_MSG_SHOW_TOP);
        mLineChar.setNormalColor(Color.parseColor("#FF0000"));
        mLineChar.feedData(lines);
        ((FrameLayout) mLineChar.getParent()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLineChar.postInvalidate();
            }
        });
    }

    private void setupCheckBox() {
        ((CheckBox) findViewById(R.id.graphshader)).setOnCheckedChangeListener(this);
        ((CheckBox) findViewById(R.id.areashader)).setOnCheckedChangeListener(this);
        ((CheckBox) findViewById(R.id.skep0)).setOnCheckedChangeListener(this);
        ((CheckBox) findViewById(R.id.select)).setOnCheckedChangeListener(this);
        ((CheckBox) findViewById(R.id.scrollable)).setOnCheckedChangeListener(this);
        ((CheckBox) findViewById(R.id.ymsg)).setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.ymsg:
                if (isChecked) {
                    mLineChar.setYaxisValues(20, 80, 3);
                } else {
                    mLineChar.setYaxisValues(0, 0, 0);
                }
                break;
            case R.id.scrollable:
                if (isChecked) {
                    mLineChar.setScrollAble(true);
                    mLineChar.setVisibleNums(10);
                } else {
                    mLineChar.setScrollAble(false);
                }
                break;
            case R.id.skep0:
                if (isChecked) {
                    mLineChar.setLineMode(LINE_DASH_0);
                } else {
                    mLineChar.setLineMode(JcoolGraph.LINE_JUMP0);
                }
                break;
            case R.id.select:
                if (isChecked) {
                    mLineChar.setSelectedMode(SELECETD_MSG_SHOW_TOP);
                } else {
                    mLineChar.setSelectedMode(SELECETD_NULL);
                }
                break;
            case R.id.graphshader:
                if (isChecked) {
                    mLineChar.setPaintShaderColors(Color.RED, Color.parseColor("#E79D23"),
                            Color.parseColor("#FFF03D"), Color.parseColor("#A9E16F"), Color.parseColor("#75B9EF"));
                } else {
                    mLineChar.setPaintShaderColors(null);
                }
                break;
            case R.id.areashader:
                if (isChecked) {
                    mLineChar.setShaderAreaColors(Color.parseColor("#4B494B"), Color.TRANSPARENT);
                } else {
                    mLineChar.setShaderAreaColors(null);
                }
        }
        mLineChar.postInvalidate();
    }

    private void setUpListview() {

        ListView graphstyle = (ListView) findViewById(R.id.graphstyle);
        final ListView linestyle = (ListView) findViewById(R.id.linestyle);
        final ListView showstyle = (ListView) findViewById(R.id.showstyle);

        linestyle.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        showstyle.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        graphstyle.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        ArrayAdapter graphstyleadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice,
                new String[]{"柱状图", "折线图"});
        ArrayAdapter linestyleadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice,
                linestyleItems);
        ArrayAdapter showstyleadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice,
                showstyleItems);

        graphstyle.setAdapter(graphstyleadapter);
        linestyle.setAdapter(linestyleadapter);
        showstyle.setAdapter(showstyleadapter);

        graphstyle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mLineChar.setGraphStyle(position);
                mLineChar.invalidate();
                ArrayAdapter linestyleadapter;
                if (position == 1) {
                    linestyle.setVisibility(View.VISIBLE);
                    linestyleadapter = new ArrayAdapter<String>(ChartActivity.this,
                            android.R.layout.simple_list_item_single_choice, showstyleItems);

                    Log.e("图表样式", "折线");

                } else {
                    linestyle.setVisibility(View.GONE);
                    linestyleadapter = new ArrayAdapter<String>(ChartActivity.this,
                            android.R.layout.simple_list_item_single_choice, barshowstyleItems);

                    Log.e("图表样式", "柱状");
                }

                showstyle.setAdapter(linestyleadapter);
            }
        });

        linestyle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mLineChar.setLineStyle(position);
                mLineChar.invalidate();

                Log.e("图表样式", "折线样式-" + position);
            }
        });

        showstyle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (linestyle.getVisibility() == View.GONE) {
                    mLineChar.setBarShowStyle(position);

                    Log.e("图表样式", "动画样式  柱状图-" + barshowstyleItems[position]);
                } else {
                    mLineChar.setLineShowStyle(position);

                    Log.e("图表样式", "动画样式 折线图-" + showstyleItems[position]);
                }
            }
        });

    }

    public void clicked(View v) {
        mLineChar.aniShow_growing();
    }

    public void changedata(View v) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<Jchart> lines = new ArrayList<>();
                for (int i = 0; i < chartNum; i++) {
                    int num = new SecureRandom().nextInt(150) + 15;
                    lines.add(new Jchart(new SecureRandom().nextInt(30), num, 0xb8e986));
                }

                mLineChar.post(new Runnable() {
                    @Override
                    public void run() {
                        mLineChar.aniChangeData(lines);
                    }
                });
            }
        }).start();
    }
}
