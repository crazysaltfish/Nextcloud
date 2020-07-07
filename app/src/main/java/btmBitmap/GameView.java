package btmBitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.myapplication.R;

/**
 * Created by Administrator on 2017/11/6 0006.
 */

public class GameView extends View {
    Bitmap btmBitmap=null;
    private int Planex=150;
    private int Planey=300;
    private int btmWidth;
    private int btmHeight;
    //在XML文件中添加控件


    public int getPlaney() {
        return Planey;
    }

    public void setPlaney(int planey) {
        Planey = planey;
    }

    public int getPlanex() {

        return Planex;
    }

    public void setPlanex(int planex) {
        Planex = planex;
    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        btmBitmap= BitmapFactory.decodeResource(getResources(), R.drawable.ggg);

        btmWidth=btmBitmap.getWidth();
        btmHeight=btmBitmap.getHeight();

    }

    //当View呈现时自动调用的方法
    @Override
    protected void onDraw(Canvas canvas) {
        //默认是在右上角绘制，所以剪去长宽的一半，在中心绘制。
        canvas.drawBitmap(btmBitmap,Planex-btmWidth/2,Planey-btmHeight/2,null);
        super.onDraw(canvas);


    }
}
