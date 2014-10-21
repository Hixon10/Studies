import cv2
import numpy as np

img = cv2.imread('detectText.bmp',cv2.CV_LOAD_IMAGE_GRAYSCALE)
startImg = cv2.imread('text.bmp')

kernel = np.ones((2,2),np.uint8)
img = cv2.erode(img,kernel,iterations = 1)


kernel = np.ones((3,5),np.uint8)
img = cv2.dilate(img, kernel, iterations = 1)

(contours, _) = cv2.findContours(img.copy(), cv2.cv.CV_RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)

for c in contours:
	x,y,w,h = cv2.boundingRect(c)
	cv2.rectangle(startImg,(x,y),(x+w,y+h),(0,255,0),1)
	cv2.rectangle(img,(x,y),(x+w,y+h),(0,255,0),2)

cv2.imwrite("binaryWords.bmp", img) 	
cv2.imwrite("detectWords.bmp", startImg) 

