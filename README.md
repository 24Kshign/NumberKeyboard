# NumberKeyboard
自定义键盘


>####思路：
>1、布局：
>
	(1)、宫格：我们可以将这个布局看成是宫格布局，然后需要计算出每个小宫格在屏幕中的位置（坐标），然后再用canvas画出相应的矩形即可。
	(2)、数字：我们需要计算出每个小宫格中的中心点位置（坐标），然后用canvas画数字上去，当然，我们知道最后一个是删除键，并不是数字，我们可以准备一张图片，将图片画到相应的位置。
>
>2、用户动作：
>
	(1)、按下：用户每一次按下的时候就表示这一次动作的开始，所以首先要将各种标识位（自定义所需要的标识位）设置成初始状态，然后需要记录按下的坐标，然后计算出用户按下的坐标与宫格中哪个点相对应，在记录相应数字。最后刷新布局
	(2)、抬起：用户抬起的时候需要刷新布局，然后将按下过程中记录下的数字或者删除键信息通过接口的形式回调给用户，最后恢复标识位
	(3)、取消：将所有的标识位恢复成初始状态。

好了，思路就讲到这里，我们来看看onDraw方法：
	
	protected void onDraw(Canvas canvas) {
		if (!isInit) {
            initData();
       }
       mPaint.setColor(Color.WHITE);
        //画宫格
        //第一排
        canvas.drawRoundRect(10, mHeight / 2 + 10, 10 + mRectWidth, mHeight / 2 + 10 + mRectHeight, 10, 10, mPaint);
        canvas.drawRoundRect(20 + mRectWidth, mHeight / 2 + 10, 20 + 2 * mRectWidth, mHeight / 2 + 10 + mRectHeight, 10, 10, mPaint);
        canvas.drawRoundRect(30 + 2 * mRectWidth, mHeight / 2 + 10, 30 + 3 * mRectWidth, mHeight / 2 + 10 + mRectHeight, 10, 10, mPaint);
        //第二排
        canvas.drawRoundRect(10, mHeight / 2 + 20 + mRectHeight, 10 + mRectWidth, mHeight / 2 + 20 + 2 * mRectHeight, 10, 10, mPaint);
        canvas.drawRoundRect(20 + mRectWidth, mHeight / 2 + 20 + mRectHeight, 20 + 2 * mRectWidth, mHeight / 2 + 20 + 2 * mRectHeight, 10, 10, mPaint);
        canvas.drawRoundRect(30 + 2 * mRectWidth, mHeight / 2 + 20 + mRectHeight, 30 + 3 * mRectWidth, mHeight / 2 + 20 + 2 * mRectHeight, 10, 10, mPaint);
        //第三排
        canvas.drawRoundRect(10, mHeight / 2 + 30 + 2 * mRectHeight, 10 + mRectWidth, mHeight / 2 + 30 + 3 * mRectHeight, 10, 10, mPaint);
        canvas.drawRoundRect(20 + mRectWidth, mHeight / 2 + 30 + 2 * mRectHeight, 20 + 2 * mRectWidth, mHeight / 2 + 30 + 3 * mRectHeight, 10, 10, mPaint);
        canvas.drawRoundRect(30 + 2 * mRectWidth, mHeight / 2 + 30 + 2 * mRectHeight, 30 + 3 * mRectWidth, mHeight / 2 + 30 + 3 * mRectHeight, 10, 10, mPaint);
        //第四排
        mPaint.setColor(Color.GRAY);
        canvas.drawRoundRect(10, mHeight / 2 + 40 + 3 * mRectHeight, 10 + mRectWidth, mHeight / 2 + 40 + 4 * mRectHeight, 10, 10, mPaint);
        mPaint.setColor(Color.WHITE);
        canvas.drawRoundRect(20 + mRectWidth, mHeight / 2 + 40 + 3 * mRectHeight, 20 + 2 * mRectWidth, mHeight / 2 + 40 + 4 * mRectHeight, 10, 10, mPaint);
        mPaint.setColor(Color.GRAY);
        canvas.drawRoundRect(30 + 2 * mRectWidth, mHeight / 2 + 40 + 3 * mRectHeight, 30 + 3 * mRectWidth, mHeight / 2 + 40 + 4 * mRectHeight, 10, 10, mPaint);


        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(60);// 设置字体大小
        mPaint.setStrokeWidth(2);
        //画数字
        //第一排
        canvas.drawText("1", xs[0], ys[0], mPaint);
        canvas.drawText("2", xs[1], ys[0], mPaint);
        canvas.drawText("3", xs[2], ys[0], mPaint);
        //第二排
        canvas.drawText("4", xs[0], ys[1], mPaint);
        canvas.drawText("5", xs[1], ys[1], mPaint);
        canvas.drawText("6", xs[2], ys[1], mPaint);
        //第三排
        canvas.drawText("7", xs[0], ys[2], mPaint);
        canvas.drawText("8", xs[1], ys[2], mPaint);
        canvas.drawText("9", xs[2], ys[2], mPaint);
        //第四排
        canvas.drawText(".", xs[0], ys[3], mPaint);
        canvas.drawText("0", xs[1], ys[3], mPaint);
        canvas.drawBitmap(mBpDelete, xs[2] - mWidthOfBp / 2 + 10, ys[3] - mHeightOfBp / 2 - 10, mPaint);
	｝
>#####注：上面的坐标需要我们自己算出，耐心一点，很容易算的，你只需要搞清楚在**Android**中屏幕是怎样的坐标系就可以了。坐标算出来之后，我们先画宫格，然后再画数字和删除键，这里有人要问了，我可以先画数字吗，NO，因为当你画完数字之后再画宫格你会发现数字不见了，为什么呢？**被你的宫格挡住了 >_<**所以千万别这样做。画的过程中别忘了将画笔设置一些你需要的属性。

好了，画好之后，我们来看看效果怎么样：

![](http://upload-images.jianshu.io/upload_images/490111-8a8ee6639c2e4bb8.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

样式出来了哈！但是设计的没那么好看，大家将就看一看哈>_< 

然后我们需要重写onTouch事件，在里面判断用户的一些行为：

	switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: //按下
            	   //恢复默认值
                setDefault();
                /**
                 *判断按下的点在哪个宫格中
                 */
                invalidate();//刷新界面
                return true;
            case MotionEvent.ACTION_UP: //弹起
                invalidate();//刷新界面
                /**
                 *一次按下结束,返回点击的数字
                 */
                //恢复默认
                setDefault();
                return true;
            case MotionEvent.ACTION_CANCEL:  //取消
                //恢复默认值
                setDefault();
                return true;
        }

如上面伪代码所示，我写了一个方法来判断按下的点在哪个宫格中：

	private void handleDown(float x, float y) {
        if (y < mHeight / 2) {
            return;
        }
        if (x >= 10 && x <= 10 + mRectWidth) {   //第一列
            clickX = xs[0];
            if (y >= mHeight / 2 + 10 && y <= mHeight / 2 + 10 + mRectHeight) {  //第一排(1)
                clickY = ys[0];
                x1 = 10;
                y1 = mHeight / 2 + 10;
                x2 = 10 + mRectWidth;
                y2 = mHeight / 2 + 10 + mRectHeight;
                number = "1";
            } else if (y >= mHeight / 2 + 20 + mRectHeight && y <= mHeight / 2 + 20 + 2 * mRectHeight) {  //第二排(4)
                x1 = 10;
                y1 = mHeight / 2 + 20 + mRectHeight;
                x2 = 10 + mRectWidth;
                y2 = mHeight / 2 + 20 + 2 * mRectHeight;
                clickY = ys[1];
                number = "4";
            } else if (y >= mHeight / 2 + 30 + 2 * mRectHeight && y <= mHeight / 2 + 30 + 3 * mRectHeight) {  //第三排(7)
                x1 = 10;
                y1 = mHeight / 2 + 30 + 2 * mRectHeight;
                x2 = 10 + mRectWidth;
                y2 = mHeight / 2 + 30 + 3 * mRectHeight;
                clickY = ys[2];
                number = "7";
            } else if (y >= mHeight / 2 + 40 + 3 * mRectHeight && y <= mHeight / 2 + 40 + 4 * mRectHeight) { //第四排(0)
                x1 = 10;
                y1 = mHeight / 2 + 40 + 3 * mRectHeight;
                x2 = 10 + mRectWidth;
                y2 = mHeight / 2 + 40 + 4 * mRectHeight;
                clickY = ys[3];
                number = ".";
            }
        } else if (x >= 20 + mRectWidth && x <= 20 + 2 * mRectWidth) {  //第二列
            clickX = xs[1];
            if (y >= mHeight / 2 + 10 && y <= mHeight / 2 + 10 + mRectHeight) {  //第一排(2)
                x1 = 20 + mRectWidth;
                y1 = mHeight / 2 + 10;
                x2 = 20 + 2 * mRectWidth;
                y2 = mHeight / 2 + 10 + mRectHeight;
                clickY = ys[0];
                number = "2";
            } else if (y >= mHeight / 2 + 20 + mRectHeight && y <= mHeight / 2 + 20 + 2 * mRectHeight) {  //第二排(5)
                x1 = 20 + mRectWidth;
                y1 = mHeight / 2 + 20 + mRectHeight;
                x2 = 20 + 2 * mRectWidth;
                y2 = mHeight / 2 + 20 + 2 * mRectHeight;
                clickY = ys[1];
                number = "5";
            } else if (y >= mHeight / 2 + 30 + 2 * mRectHeight && y <= mHeight / 2 + 30 + 3 * mRectHeight) {  //第三排(8)
                x1 = 20 + mRectWidth;
                y1 = mHeight / 2 + 30 + 2 * mRectHeight;
                x2 = 20 + 2 * mRectWidth;
                y2 = mHeight / 2 + 30 + 3 * mRectHeight;
                clickY = ys[2];
                number = "8";
            } else if (y >= mHeight / 2 + 40 + 3 * mRectHeight && y <= mHeight / 2 + 40 + 4 * mRectHeight) { //第四排(0)
                x1 = 20 + mRectWidth;
                y1 = mHeight / 2 + 40 + 3 * mRectHeight;
                x2 = 20 + 2 * mRectWidth;
                y2 = mHeight / 2 + 40 + 4 * mRectHeight;
                clickY = ys[3];
                number = "0";
            }
        } else if (x >= 30 + 2 * mRectWidth && x <= 30 + 3 * mRectWidth) {   //第三列
            clickX = xs[2];
            if (y >= mHeight / 2 + 10 && y <= mHeight / 2 + 10 + mRectHeight) {  //第一排(3)
                x1 = 30 + 2 * mRectWidth;
                y1 = mHeight / 2 + 10;
                x2 = 30 + 3 * mRectWidth;
                y2 = mHeight / 2 + 10 + mRectHeight;
                clickY = ys[0];
                number = "3";
            } else if (y >= mHeight / 2 + 20 + mRectHeight && y <= mHeight / 2 + 20 + 2 * mRectHeight) {  //第二排(6)
                x1 = 30 + 2 * mRectWidth;
                y1 = mHeight / 2 + 20 + mRectHeight;
                x2 = 30 + 3 * mRectWidth;
                y2 = mHeight / 2 + 20 + 2 * mRectHeight;
                clickY = ys[1];
                number = "6";
            } else if (y >= mHeight / 2 + 30 + 2 * mRectHeight && y <= mHeight / 2 + 30 + 3 * mRectHeight) {  //第三排(9)
                x1 = 30 + 2 * mRectWidth;
                y1 = mHeight / 2 + 30 + 2 * mRectHeight;
                x2 = 30 + 3 * mRectWidth;
                y2 = mHeight / 2 + 30 + 3 * mRectHeight;
                clickY = ys[2];
                number = "9";
            } else if (y >= mHeight / 2 + 40 + 3 * mRectHeight && y <= mHeight / 2 + 40 + 4 * mRectHeight) { //第四排(删除键)
                x1 = 30 + 2 * mRectWidth;
                y1 = mHeight / 2 + 40 + 3 * mRectHeight;
                x2 = 30 + 3 * mRectWidth;
                y2 = mHeight / 2 + 40 + 4 * mRectHeight;
                clickY = ys[3];
                number = "delete";
            }
        }
    }
>注：这个方法跟你之前计算出的宫格坐标有关系，所以一定不要计算错误

至此，我们写的差不多了，然后就是要提供一个接口，对外开放，方便用的时候调用，获取到数字或者其他信息：

	public interface OnNumberClickListener {
        //回调点击的数字
        public void onNumberReturn(String number);

        //删除键的回调
        public void onNumberDelete();
    }
在onTouch事件中使用：

    case MotionEvent.ACTION_UP: //弹起
                invalidate();//刷新界面
                //一次按下结束,返回点击的数字
                if (onNumberClickListener != null) {
                    if (number != null) {
                        if (number.equals("delete")) {
                            onNumberClickListener.onNumberDelete();
                        } else {
                            onNumberClickListener.onNumberReturn(number);
                        }
                    }
                }
                //恢复默认
                setDefault();
                return true;
 然后我们来看一下效果怎么样吧！

![](http://upload-images.jianshu.io/upload_images/490111-3d5237832e8f71f4.gif?imageMogr2/auto-orient/strip)

功能也实现了，可是强迫症很强的我看着很不舒服，不知道你们有没有，好歹这也是一个键盘吧！按下弹起的效果都没有（没有改变按下的背景），在这里我们设置一个标志位，按下弹起刷新界面就可以了。在onTouch事件中改变该标识位的值，然后在onDraw方法中判断该标识位即可：

onTouch方法中增加：

	case MotionEvent.ACTION_DOWN: //按下
		type=0;
	case MotionEvent.ACTION_UP: //弹起
		type=1;
		
onDraw方法增加：

	        if (clickX > 0 && clickY > 0) {
            if (type == 0) {  //按下刷新
                if (number.equals("delete")) {
                    mPaint.setColor(Color.WHITE);
                    canvas.drawRoundRect(x1, y1, x2, y2, 10, 10, mPaint);
                    canvas.drawBitmap(mBpDelete, xs[2] - mWidthOfBp / 2 + 10, ys[3] - mHeightOfBp / 2 - 10, mPaint);
                } else {
                    if (number.equals(".")) {
                        mPaint.setColor(Color.WHITE);
                    } else {
                        mPaint.setColor(Color.GRAY);
                    }
                    canvas.drawRoundRect(x1, y1, x2, y2, 10, 10, mPaint);
                    mPaint.setColor(Color.BLACK);
                    mPaint.setTextSize(60);// 设置字体大小
                    mPaint.setStrokeWidth(2);
                    canvas.drawText(number, clickX, clickY, mPaint);
                }
            } else if (type == 1) {  //抬起刷新
                if (number.equals("delete")) {
                    mPaint.setColor(Color.GRAY);
                    canvas.drawRoundRect(x1, y1, x2, y2, 10, 10, mPaint);
                    canvas.drawBitmap(mBpDelete, xs[2] - mWidthOfBp / 2 + 10, ys[3] - mHeightOfBp / 2 - 10, mPaint);
                } else {
                    if (number.equals(".")) {
                        mPaint.setColor(Color.GRAY);
                    } else {
                        mPaint.setColor(Color.WHITE);
                    }
                    canvas.drawRoundRect(x1, y1, x2, y2, 10, 10, mPaint);
                    mPaint.setColor(Color.BLACK);
                    mPaint.setTextSize(60);// 设置字体大小
                    mPaint.setStrokeWidth(2);
                    canvas.drawText(number, clickX, clickY, mPaint);
                }
                //绘制完成后,重置
                clickX = 0;
                clickY = 0;
            }
        }
接下来再来看看效果吧：

![](http://upload-images.jianshu.io/upload_images/490111-aecfc093b1db1a2d.gif?imageMogr2/auto-orient/strip)

现在看起来舒服多了，~_~
