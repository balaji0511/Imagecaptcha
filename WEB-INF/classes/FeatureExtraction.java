/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package textclasification;

public class FeatureExtraction {
    public static int wordWeight(String text,String word)
    {
    int weight=0;
    int wordCount=text.split(" ").length;
    int featureCount=countWordsInText(text.toLowerCase(),word.toLowerCase());
    weight=(featureCount);//*100)/wordCount;
    return weight;
    }
    
public static int countWordsInText(String Page,String pattern)
{
int count=0;
int start=0;
int end=0;
String titleTag=Page;
int i=1;
while(i>0)
{
i=titleTag.indexOf(pattern);
titleTag=titleTag.substring(i+1,titleTag.length());
//System.out.println("------------------------\n"+pattern);
count++;
}
return count-1;
}
    
    
}
