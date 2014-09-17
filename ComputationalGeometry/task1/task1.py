polygon = []
points = []

strN = input()
N = int(strN)

for i in range(0,N):
   s = input()
   pair = s.replace(")", "").replace("(", "").split(",")
   polygon.append(tuple([int(pair[0]), int(pair[1])]))

strM = input()
M = int(strM)

for i in range(0,M):
   s = input()
   pair = s.replace(")", "").replace("(", "").split(",")
   points.append(tuple([int(pair[0]), int(pair[1])]))


   
def vecMul(a, b):
   return a[0]*b[1]-a[1]*b[0] 
        
def scalMul(a, b):
   return a[0]*b[0]+a[1]*b[1] 

def isPointInSector(a, b, s):
   if a == s or b == s:
      return True
      
   v1 = (a[0]-b[0], a[1]-b[1])
   v2 = (a[0]-s[0], a[1]-s[1])
   
   if vecMul(v1, v2) == 0 and ((a[0] < s[0] < b[0]) or (a[0] > s[0] > b[0])):
      return True
   
   return False
      
   
# a, b - концы отрезка
# s - стартовая точка луча
# e - точка, задающая направления луча

# возвращаемые значения:
# 0 - нет пересечений
# 1 - отрезок пересекается с лучом
# 2 - стартовая точка луча принадлежит отрезку
# 3 - отрезок или его часть принадлежит лучу
# 4 - общая точка - первая вершина отрезка
# 5 - общая точка - вторая вершина отрезка
def beamAndSegmentIntersection(a, b, s, e):
   
   if isPointInSector(a, b, s):
      return 2
      
   v1 = (a[0]-s[0], a[1]-s[1]) 
   v2 = (b[0]-s[0], b[1]-s[1])
   v =  (e[0]-s[0], e[1]-s[1])
   
   v1v = vecMul(v1, v)
   vv2 = vecMul(v, v2)
   v1v2 = vecMul(v1, v2)
   
   if v1v2 == 0:
      if v1v == 0:
         scalV1V = scalMul(v1, v)
         scalV2V = scalMul(v2, v)
         if scalV1V > 0 or scalV2V > 0:
            #часть отрезка или весь лежит на луче
            return 3#"segment intersec beam"
         if scalV1V == 0 or scalV2V == 0:
            #общая точка - вершина луча
            return 2#"point in segment"
         if scalV1V < 0 and scalV2V < 0:
            return 0#"False"
      else:
         if scalMul(v1, v2) > 0:
            return 0#"False"
         else:
            #общая точка - вершина луча
            return 2#"point in segment"
            
   
   if v1v == 0:
      if scalMul(v1, v) > 0:
         #общая точка - первая вершина отрезка
         return 4#"point in segment"
      else:
         return 0 #"False"
   
   
   if vv2 == 0:
      if scalMul(v, v2) > 0:
         #общая точка - вторая вершина отрезка
         return 5#"point in segment"
      else:
         return 0#"False"

   if v1v*vv2 > 0 and vv2*v1v2>0:     
      return 1#"True"
   else:
      return 0#"False"
      

def isDifSide(preP, nextP, s, e):
   b1 = beamAndSegmentIntersection(preP, nextP, e, s)
   b2 = beamAndSegmentIntersection(preP, nextP, s, e)
   if b1==1 or b2==1:
      return True
   return False
            
def pointInPolygon(polygon, p):
   e = (p[0]-1, p[1])
   count = 0
   for i in range(0, len(polygon)):
      flag = beamAndSegmentIntersection(polygon[i], polygon[(i+1)%len(polygon)], p, e)
      if flag == 2:
         return True
      if flag == 1: 
         count += 1;
      if flag == 4: 
         preP = polygon[ len(polygon)-1 if i-1 < 0 else i-1]
         nextP = polygon[(i+1)%len(polygon)] 
         if isDifSide(preP, nextP, p, e):
            count += 1;
      if flag == 3:
         if len(polygon) == 3:
            return False
         preP = polygon[ len(polygon)-1 if i-1 < 0 else i-1]
         nextP = polygon[(i+2)%len(polygon)]
         if isDifSide(preP, nextP, p, e):
            count += 1;
          
   if count%2 == 1:
      return True
   return False
   
for p in points:
   if pointInPolygon(polygon, p):
      print("yes")
   else:
      print("no")
