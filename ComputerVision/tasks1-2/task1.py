import cv2
import numpy as np

img = cv2.imread('text.bmp',cv2.CV_LOAD_IMAGE_GRAYSCALE)

img = cv2.GaussianBlur(img,(7, 7),0)

img = cv2.Laplacian(img, 3)

img = np.array(img * 255, dtype = np.uint8)
ret,img = cv2.threshold(img,0,255,cv2.THRESH_BINARY+cv2.THRESH_OTSU)  
  
cv2.imwrite("detectText.bmp", img)   


