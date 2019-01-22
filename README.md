# CircleAnimationAndVerMenuLayout
一个圆形散开动画，并且icon可点击/一个垂直平均布局layout，item也可以点击

![](https://raw.githubusercontent.com/machao0727/CircleAnimationAndVerMenuLayout/master/simplegif/GIF.gif)

USE
====

```java
        *VerticalMenuView
        menuView.setText("测试文本1", "测试文本2", "测试文本3", "测试文本4", "测试文本5", "测试文本6", "测试文本7");
        menuView.setOnItemClickListener(new VerticalMenuView.onItemClickListener() {
            @Override
            public void itemClick(int position) {
                Toast.makeText(MainActivity.this, "item" + position, Toast.LENGTH_SHORT).show();
            }
        });

        *CloudView
        cloudView.setIcon(R.mipmap.ic_avater_five, R.mipmap.ic_avater_four, R.mipmap.ic_avater_six, R.mipmap.ic_avater_one,
                 R.mipmap.ic_avater_three, R.mipmap.ic_avater_two, R.mipmap.ic_avater_three, R.mipmap.ic_avater_two, R.mipmap.ic_avater_five, R.mipmap.ic_avater_four);
        cloudView.setOnItemClickListener(new CloudView.onItemClickListener() {
                @Override
                public void itemClick(int position) {
                    Toast.makeText(MainActivity.this, "item" + position, Toast.LENGTH_SHORT).show();
                }
            });
```


