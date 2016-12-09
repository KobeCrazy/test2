##软键盘测试工具 
###目前支持功能
1、正常文本、数字、邮箱地址模拟输入 

2、联系人插入功能实现

3、支持语音前台、后台播放输入

      *前台播放
       模拟点击Play按键
       
      *后台播放（使用broadcast）
       开启播放：adb shell am broadcast -a com.forimetest.MainActivity  --ez PlayerVoice true
       关闭播放：adb shell am broadcast -a com.forimetest.MainActivity  --ez PlayerVoice false
