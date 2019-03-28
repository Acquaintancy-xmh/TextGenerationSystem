import synonyms
import sys

if __name__ == '__main__':
    keywordList = sys.argv[1].split(",")
    subjectList = sys.argv[2].split(",")
    size = int(sys.argv[3])
    maxValueList = []
    for keyword in keywordList:
        for subject in subjectList:
            r = synonyms.compare(keyword, subject)
            result = [r, keyword, subject]
            if len(maxValueList) < size:
                maxValueList.append(result)
            else:
                maxValueList.sort(reverse=True)
                if maxValueList[size-1][0] < result[0]:
                    maxValueList[size-1] = result
                    maxValueList.sort(reverse=True)
    print("(",end="")
    print(maxValueList,end="")
    print(")")
    # for keyWord in keywordList:
    #     for subject in subjectList:
    #         result = [synonyms.compare(keyWord, subject), keyWord, subject]
    #         print(result)
