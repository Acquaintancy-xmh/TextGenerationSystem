import synonyms
import sys

if __name__ == '__main__':
	seedWord = sys.argv[1]
	wordString = sys.argv[2]
	valueList = []
	wordList = wordString.split(",")
	for word in wordList:
		valueList.append(synonyms.compare(seedWord,word))
	print("(",end = '')
	print(valueList,end = '')
	print(")")	


