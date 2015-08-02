#encoding:utf-8

import os,re

# open a java file
# read line from file one by one
# if the line contains package, *, class and blank line, then rewrite to a new java.tmp file
# if the line start with "private", then 
# fs = os.walk(os.getcwd())

def main():
	for parent, dirnames, filenames in os.walk(os.getcwd()):
		for filename in filenames:
			if ".java" in filename:
				# print "parent:" + parent  
				# print "filename:" + os.path.join(parent,filename)
				# print "------"
				change_file(os.path.join(parent,filename))
				print "success"
	# change_file("/Users/DaoCaoWu/Documents/Github/ADao12/DCWFrameworkForAndroid/DCWFramework/dcwdianping/src/main/java/com/dcw/app/rating/biz/pojo/book/Review.java")

def issubstr(substrlist,str):  
    ''''' 
    #判断字符串Str是否包含序列SubStrList中的每一个子字符串 
    #>>>SubStrList=['F','EMS','txt'] 
    #>>>Str='F06925EMS91.txt' 
    #>>>IsSubString(substrlist,Str)#return True (or False) 
    '''  
    flag=True  
    for substr in substrlist:  
        if not(substr in str):  
            flag=False  
  
    return flag 

def change_file(filename):
	fin = open(u'%s' %(filename))
	fout = open(u'%s.tmp' %(filename), 'w')
	for eachline in fin:
		outline = read_line(eachline)
		fout.write("%s" %(outline))
	pass
	fin.close()
	fout.close()
	if os.path.exists(filename):
		os.remove(filename) 
	else:
		print 'no such file:%s' % filename
	os.rename('%s.tmp' %(filename), filename)

def read_line(str):
	if contains(str, ("package", "class", "*", "{", "}")):
		# if "package" in str:
		# 	str = "%s\n%s\n" %(str, "import com.google.gson.annotations.SerializedName;")
		return str
	elif contains(str, ("private", "String")):
		words = str.lstrip(' ').split(' ')
		for word in words:
			json_name = words[2].split(';')[0]
			print get_str(json_name, '_'), words[2].split(';')[0]
			if cmp(get_str(json_name, '_'),words[2].split(';')[0]) == 0:
				return str
			else:
				var_name = get_str(words[2], '_')
				outStr = '\t@SerializedName("%s")\n' %(json_name)
				outStr = "%s\t%s %s %s" %(outStr, words[0], words[1], var_name)
				return outStr
			pass
	else:
		return str
	pass

def get_str(oriStr,splitStr):
     str_list = oriStr.split(splitStr)
     if len(str_list) > 1:
         for index in range(1, len(str_list)):
             if str_list[index] != '':
                 str_list[index] = str_list[index][0].upper() + str_list[index][1:]
             else:
                 continue
         return ''.join(str_list)
     else:
         return oriStr

def contains(source, target):
	if any(str_ in source for str_ in target):
		return True
	return False



if __name__ == '__main__':
	main()

