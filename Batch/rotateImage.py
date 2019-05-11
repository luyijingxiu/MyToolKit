'''
问题背景：
某文件夹下的图片都是方向不正确的图片
需要顺时针旋转90度

方案：
使用pillow图片处理库
获取到图片，旋转然后覆盖文件

不足：
路径以及文件范围是硬编码到代码里面的，没有研究命令行参数怎么用的
'''
from PIL import Image
 
#路径
path="E:/Data/2013/"

#文件范围
for index in range(102,156):
	num=str(index)
	imageName=num+".jpg"
	if (index < 10):
		imageName="0"+imageName
	imageName=path+imageName
	#读取图像
	im = Image.open(imageName)
	#im.show()
	 
	# 指定逆时针旋转的角度
	im_rotate = im.rotate(270,0,1) 
	#im_rotate.show()
	im_rotate.save(imageName)