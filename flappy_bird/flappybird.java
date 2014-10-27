public class MainActivity extends Activity
{
 static final int IMG_WIDTH=384;	//图片的长宽
 static final int IMG_HEIGHT=512;
 private View viewDraw;

 @Override
 protected void onCreate(Bundle savedInstanceState)
 {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_main);
  viewDraw = (View) findViewById(R.id.viewDraw);
  MyView v = new MyView(viewDraw.getContext(), null);
  
  setContentView(v);
 }

 @Override
 public boolean onKeyDown(int keyCode, KeyEvent event)
 {
  if (keyCode == KeyEvent.KEYCODE_BACK)
  {
   finish();
   return true;
  }
  return super.onKeyDown(keyCode, event);
 }
 //定义水管障碍物类
 class Obstacle
 {
  int x;
  int h;
  public Obstacle()
  {
   x=0;
   h=0;
  }
 }
 //自定义View子类，完成动画的刷新
 class MyView extends View implements Runnable
 {
  static final int SPEED=2;
  // 图形当前坐标
  private int x = 20, y = 20,a,b,c,birdHeight,time,point,v,record;
  Obstacle pillar[]= new Obstacle[3];  // 三个管道障碍
  
  boolean bInit,bCourse,bGame,bGameOver;
  
  RefreshHandler mRedrawHandler;
  
  Bitmap birdUpBitmap,birdDownBitmap,birdMiddleBitmap,groundBitmap,otherStartBitmap;
  Bitmap courseUpBitmap,courseDownBitmap,courseMiddleBitmap;
  Bitmap pillarUpBitmap,pillarDownBitmap,gameOverBitmap,recordBitmap,playAgainBitmap,listBitmap,goldBitmap,silverBitmap,bronzeBitmap,whiteGoldBitmap,gameBackgroundBitmap;

  // 构造方法
  public MyView(Context context, AttributeSet attrs)
  {
   super(context, attrs);
   // TODO Auto-generated constructor stub
   // 获得焦点
   setFocusable(true);
   bInit=false;
   bCourse=false;
   bGame=false;
   bGameOver=false;
   //障碍物初始化  
   pillar[0]=new Obstacle();
   pillar[1]=new Obstacle();
   pillar[2]=new Obstacle();
   //载入图片  
   otherStartBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.other_start);
   birdUpBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bird_up);
   birdDownBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bird_down);
   birdMiddleBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bird_middle);
   groundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.background_ground); 
   pillarDownBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pillar_downside);
   pillarUpBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pillar_upside);
   gameOverBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.other_gameover);
   recordBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.other_record);
   playAgainBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.other_restart);
   listBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.other_list);
   bronzeBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.medals_bronze);
   silverBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.medals_silver);
   goldBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.medals_gold);
   whiteGoldBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.medals_whitegold);
   //游戏界面刷新handler  
   mRedrawHandler = new RefreshHandler();
   
   // 启动线程
   new Thread(this).start();
  }
  
  @Override
  public void run()
  {
   // TODO Auto-generated method stub

   while (true)
   {
    // 通过发送消息更新界面
    Message m = new Message();
    m.what = 0x101;
    mRedrawHandler.sendMessage(m);
    try
    {
     Thread.sleep(20);
    } catch (InterruptedException e)
    {
     // TODO Auto-generated catch block
     e.printStackTrace();
    }
   }
  }

  //游戏画面更新
  void gameDraw(Canvas canvas)
  {
   if(!bInit)  //  游戏初始化
   {
    Bitmap bm;
    bm = BitmapFactory.decodeResource(getResources(), R.drawable.other_load);
    canvas.drawBitmap(bm, 0, 0, null); 
    Paint p1 = new Paint();
    p1.setAntiAlias(true);
    p1.setColor(Color.WHITE);
    p1.setTextSize(20);//设置字体大小
   }
   else if(bInit)
   {
   ////////////////////////////////////////////////////////////////////
   //    bCourse  开始欢迎界面标志，  false时  地面移动，bird在动
   //    true  再点击开始游戏   （ 欢迎界面）
    if(!bCourse)   //    a       b        c       speed =2;
    {
     a-=SPEED;
     if(a<=0)
      a=384;
     
     canvas.drawBitmap(otherStartBitmap, 0, 0, null);
     canvas.drawBitmap(groundBitmap, a, 448, null);
     canvas.drawBitmap(groundBitmap, a-384, 448, null);  //  地面移动 效果
	 
     ////////////////////////////////////////////////////////////
	 //   绘制   中-》上-》中-》下  循环动态画面
     b=a%128;
     if(b>=0&&b<32)
     {
      canvas.drawBitmap(birdMiddleBitmap, 175, 220, null);
     }
     if(b>=32&&b<64)
     {
      canvas.drawBitmap(birdUpBitmap, 175, 216, null);
     }
     if(b>=64&&b<96)
     {
      canvas.drawBitmap(birdMiddleBitmap, 175, 220, null);
     } 
     if(b>=96&&b<=128)
     {
      canvas.drawBitmap(birdDownBitmap, 175, 224, null);
     }
     canvas.drawBitmap(groundBitmap, a, 448, null);
	 
    }
	///////////////////////////////////////////////////////////////////////////////////////
	//   bGame  开始游戏标志，
	//
	//
    else if (bCourse)
    {
     if(!bGame)
     {
      time += 1;
      
      int temp = time % 64;
      if (temp >= 0 && temp < 16)
       canvas.drawBitmap(courseUpBitmap, 0, 0, null);
      if (temp >= 16 && temp < 32)
       canvas.drawBitmap(courseMiddleBitmap, 0, 0, null);
      if (temp >= 32 && temp < 48)
       canvas.drawBitmap(courseDownBitmap, 0, 0, null);
      if (temp >= 48 && temp < 64)
       canvas.drawBitmap(courseMiddleBitmap, 0, 0, null);
      a-=SPEED;
      if(a<=0)
       a=384;
      canvas.drawBitmap(groundBitmap, a, 448, null);
      canvas.drawBitmap(groundBitmap, a-384, 448, null);
     
     }
	 /////////////////////////////////////////////
	 //  bGame  开始游戏
	 //   v 重力加速度
     else if(bGame)
     {
      if(!bGameOver)
      {
       time+=1;
       
       /***************计算高度************************/ 
	   //  if(!bGameOver)
       //    v-=250;          birdHeight=150;
	   //  y 轴向下为增
       v+=9.8;
       if(v>120)
        v=120;
       else if(v<-150)
        v=-150;
       if(v>=0)
        birdHeight+=((v*5.0)/77);
       else if(v<0)
        birdHeight+=((v*4.5)/77);
       if(birdHeight<0)
        birdHeight=0;
       else if(birdHeight>415)
        birdHeight=415;
       
       /***************柱子的移动**********************/
	   //    三个柱子
        for(c=0;c<3;c++)
        {
         pillar[c].x-=SPEED;
         if(pillar[c].x<=-70)
          pillar[c].x=650;
        if(pillar[c].x==512)
        {
         pillar[c].h=(new Random()).nextInt(200)+200;
        }     
        }
        
       /***************计算分数************************/
       for(c=0;c<3;c++)
       {
        if(pillar[c].x==100)
         point++;
       }
       
       /****************地面移动***********************/
       a-=SPEED;
       
       /****************判断碰撞,+32判断下侧柱子，-170判断上侧柱子***********************/
        if(pillar[0].x>=30&&pillar[0].x<=145&&(pillar[0].h<(birdHeight+32)||(pillar[0].h-170)>birdHeight))
         bGameOver=true;
        if(pillar[1].x>=30&&pillar[1].x<=145&&(pillar[1].h<(birdHeight+32)||(pillar[1].h-170)>birdHeight))
         bGameOver=true;
       if(pillar[2].x>=30&&pillar[2].x<=145&&(pillar[2].h<(birdHeight+32)||(pillar[2].h-170)>birdHeight))
        bGameOver=true;
       //是否碰到地面
       if(birdHeight>414)
        bGameOver=true;
      }
      
      
      /***************显示图像************************/   
      //显示背景 
      canvas.drawBitmap(gameBackgroundBitmap, 0, 0, null);
      
      //显示柱子
      for(c=0;c<3;c++)
      {
       canvas.drawBitmap(pillarDownBitmap, pillar[c].x, pillar[c].h, null);
       canvas.drawBitmap(pillarUpBitmap, pillar[c].x, pillar[c].h-420, null);
      }
      
      if(a<=0)
       a=384;
      canvas.drawBitmap(groundBitmap, a, 448, null);
      canvas.drawBitmap(groundBitmap, a-384, 448, null);
      
      
      int temp=time%16;
      if(temp>=0&&temp<4)
       canvas.drawBitmap(birdMiddleBitmap, 100, birdHeight, null);
      if(temp>=4&&temp<8)
       canvas.drawBitmap(birdUpBitmap, 100, birdHeight, null);
      if(temp>=8&&temp<12)
       canvas.drawBitmap(birdMiddleBitmap, 100, birdHeight, null);
      if(temp>=12&&temp<16)
       canvas.drawBitmap(birdDownBitmap, 100, birdHeight, null);
      
      
      if(!bGameOver)
      {
       /*******************显示分数**********************/
       Paint p1 = new Paint();
       p1.setAntiAlias(true);
       p1.setColor(Color.WHITE);
       p1.setTextSize(20);//设置字体大小
       canvas.drawText("score:"+point, 171, 50, p1);
       canvas.drawText("acc:"+v, 171, 80, p1);
       canvas.drawText("H:"+birdHeight, 171, 110, p1);
      }
      else if(bGameOver)
      {
       canvas.drawBitmap(gameOverBitmap, 55, 60, null);
       canvas.drawBitmap(recordBitmap, 35, 150, null);
       canvas.drawBitmap(playAgainBitmap, 35, 350, null);
       canvas.drawBitmap(listBitmap, 200, 350, null);
             
       if(point>=10&&point<20)
        canvas.drawBitmap(bronzeBitmap, 67, 200, null);
       if(point>=20&&point<30)
        canvas.drawBitmap(silverBitmap, 67, 200, null);
       if(point>=30&&point<40)
        canvas.drawBitmap(goldBitmap, 67, 200, null);
       if(point>=40)
        canvas.drawBitmap(whiteGoldBitmap, 67, 200, null);
       
       Paint p1 = new Paint();
       p1.setAntiAlias(true);
       p1.setColor(Color.WHITE);
       p1.setTextSize(20);//设置字体大小
       canvas.drawText(""+point, 260, 210, p1);
       //最高纪录
       canvas.drawText("1000", 260, 268, p1);
       
      }
     }
    }
   } 
   
   // 实例化画笔
   Paint p = new Paint();
   p.setColor(Color.BLACK);
   p.setColor(Color.WHITE);
   canvas.drawText("init: "+bInit+"   course: "+bCourse+"    game: "+bGame+"    over: "+bGameOver, 0, 10, p);
  }
  
  @Override
  protected void onDraw(Canvas canvas)
  {
   // TODO Auto-generated method stub
   super.onDraw(canvas);
   canvas.drawColor(Color.BLACK);
   //这里的操作是为了在我的平板电脑竖屏尽量全屏显示，原图片很小，我的屏幕分辨率为1280*800，如果不需要对图片进行缩放，可以在只而立只保留gameDraw（）部分         
   canvas.save();
   canvas.translate(16, 80);
   float scale=(float) 2.0;
   canvas.scale(scale, scale);
   canvas.clipRect(0, 0, IMG_WIDTH, IMG_HEIGHT);
   gameDraw(canvas);
   canvas.restore();
  }

  // 更新界面处理器
  class RefreshHandler extends Handler
  {
   @Override
   public void handleMessage(Message msg)
   {
    // TODO Auto-generated method stub
    if (msg.what == 0x101)
    {
     MyView.this.update();
     MyView.this.invalidate();
    }
    super.handleMessage(msg);
   }
  }

  // 更新坐标
  private void update()
  {
  }
  //触屏事件 
  @Override
  public boolean onTouchEvent(MotionEvent event)
  {
   switch (event.getPointerCount())
   {
   case 1:
    return onSingleTouchEvent(event);
   case 2:
    return onDoubleTouchEvent(event);
   default:
    return false;
   }
  }
  //单手指触屏处理
  private boolean onSingleTouchEvent(MotionEvent event)
  {
   int x = (int) event.getX();
   int y = (int) event.getY();

   switch (event.getAction())
   {
   case MotionEvent.ACTION_UP:
    if(!bInit)
    {     
     a=-2;
     bInit=true; 
    }
    else if(bInit)
    {
     if(!bCourse)
     {
      if((new Random()).nextInt(3)==0)
      {
       gameBackgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.background_night);
       courseDownBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.intro_night_down);
       courseUpBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.intro_night_up);
       courseMiddleBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.intro_night_middle);
      }
      else
      {
       gameBackgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.background_day);
       courseDownBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.intro_day_down);
       courseUpBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.intro_day_up);
       courseMiddleBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.intro_day_middle);
      } 
      time = 0;
      a=0;
      
      bCourse=true; 
     }
     else if(bCourse)
     {
      if(!bGame)
      {
       time=0;
       birdHeight=150;
       point=0;
       pillar[0].x=700;
       pillar[1].x=940;
       pillar[2].x=1180;
       a=0;
       b=0;
       c=0;
       
       bGame=true;
      }
      else if(bGame)
      {
       if(!bGameOver)
        v-=250;
       else if(bGameOver)
       {
        bGameOver=false;
        bCourse=false;
        bGame=false;
       }
      }
     }
    }
    break;
   case MotionEvent.ACTION_DOWN:

    break;
   case MotionEvent.ACTION_MOVE:
    
    break;
   }
   return true;
  }
  //双指触屏处理
  private boolean onDoubleTouchEvent(MotionEvent event)
  {
   switch (event.getAction() & MotionEvent.ACTION_MASK)
   {
   case MotionEvent.ACTION_POINTER_UP:

    break;
   case MotionEvent.ACTION_POINTER_DOWN:
   {

    break;
   }
   case MotionEvent.ACTION_MOVE:

    postInvalidate();
    break;
   }
   return true;
  }
 }
}