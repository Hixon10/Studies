import cv
import cv2
import numpy as np

img = cv2.imread('detectText.bmp',cv2.CV_LOAD_IMAGE_GRAYSCALE)
startImg = cv2.imread('text.bmp')

kernel = np.ones((2,2),np.uint8)
img = cv2.erode(img,kernel,iterations = 1)


kernel = np.ones((3,5),np.uint8)
img = cv2.dilate(img, kernel, iterations = 1)

flooded = img.copy()
cols, rows = flooded.shape
mask = np.zeros((cols+2, rows+2), np.uint8)
    
for y in range(rows):
	for x in range(cols):
		pix = flooded[x,y]
		seed_pt = y, x
		if pix == 0:
			continue
		retval, rect = cv2.floodFill(flooded, mask, seed_pt, 0, 0, 0, 
			4 | cv2.FLOODFILL_FIXED_RANGE)
		x,y,w,h = rect
		cv2.rectangle(startImg,(x,y),(x+w,y+h),(0,255,0),1)

cv2.imwrite("binaryWords.bmp", img) 	
cv2.imwrite("detectWords.bmp", startImg) 

