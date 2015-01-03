##The is a corpus processing package matching a given En-Ch corpus 

---
###Information about corpus

* Bilingual corpus : English - Chinese
* Size : 1.1 GB
* Format like this :
```
<corpus id="ch_news">
<doc id="AFC20020702.0046">
<sentence sid="0">
<src> 智利 最高 法院 裁决 皮诺契 特 丧失 心神 能力 </src>
<trg> c6hilean supreme court rules pinochet mentally incompetent </trg>
<wa> 0-0 1-1 2-2 3-4 4-3 4-4 4-5 4-6 5-4 6-4 7-4 8-6 </wa>
</sentence>
<sentence sid="1">
<src> 最高 法院 以 四 对 一 票 的 裁决 , 结束 了 为 使 八十六 岁 的 皮诺契特 以 他 被 控告 的 罪名 受审 的 两 年 诉讼 , 这 名 退休 将军 涉及 派出 一个 名为 「 死亡 车队 」 的 军方 小组 , 暗杀 政敌 。 </src>
<trg> the supreme court 's 4-1 ruling ended the two - year prosecution to bring the 86 - year - old pinochet to trial for his charged crimes . the retired general was involved in dispatching a military squad called " death caravan " to assassinate his political opponents . </trg>
 <wa> 0-1 1-2 2-4 3-4 6-4 7-3 8-5 10-6 12-12 13-13 14-15 15-16 15-17 15-18 15-19 17-4 17-20 18-21          19-24 20-25 21-25 23-26 24-22 24-23 26-8 27-10 28-11 29-27 30-28 32-29 33-30 34-32 35-34 36-35          37-38 38-39 39-40 40-41 41-42 43-36 44-4 46-37 46-44 47-45 47-46 47-47 48-48 </wa>
</sentence>
</doc>
</corpus>
```
* Download link: (Click Here)[http://]
###Build the package
Unpackage the package to your workspace , then type the command :
```
$ ant
```
After finishing it , a jar file named percotor.jar will be build , which is runnable . The runnable jar file is what we need . But if build failed , we need update jdk to jdk1.8.
We just can get the brife direction after type :
```
$java -jar percolator.jar
```
and we will see 
```
 percolator [options...] [arguments...]
 -dfile VAL  : Specify data file !
 -dir VAL    : Specify directory of data and output!
 -dstops VAL : Specify the stopwords file !
 -filter N   : Specify whether we want to get rid of words with low frequence !
 -ndocs N    : Stecity number of documents you want !
 -plain      : Specify whether we want to get the plain text !
 -ridstops   : Specify whether we want to get rid of the stopsword !
```
###Processing the corpus to what you need

* Plain:
```
$java -jar percolator.jar -plain -ndocs 1000 -dir /home/cyno/corpus/ -dfile all.xm.wa
```
If you need to extract 10000 documents , you should change 1000 to 10000 . The configures of "-dir" and "-dfile" are the path and the name of corpus file you keep .
After typing this , you will get two file named plain.ch and plain.en , whose formats likes following :
```
智利 最高 法院 裁决 皮诺契 特 丧失 心神 能力 最高 法院 以 四 对 一 票 的 裁决 , 结束 了 为 使 八十六 岁 的 皮诺契特 以 他 被 控告 的 罪名 受审 的 两 年 诉讼 , 这 名 退休 将军 涉及 派出 一个 名为 「 死亡 车队 」 的 军方 小组 , 暗杀 政敌 。
```
```
c6hilean supreme court rules pinochet mentally incompetent the supreme court 's 4-1 ruling ended the two - year prosecution to bring the 86 - year - old pinochet to trial for his charged crimes .
```
* Get rid of stopwords:
```
$java -jar percolator.jar -ridstops -dir /home/cyno/corpus/ -dfile all.xm.wa -dstops stopfile
```
Compared to plain command , there are one more configure dstops , which is the prefix of stopwords files . Stopwords files must be separated two , the suffix is named after language . For example , stopfile.ch and stopfile.en are well .

* Filter the words with low frequency:
```
&java -jar percolator.jar -filter 2 -dir /home/cyno/corpus/ -dfile all.xm.wa -dstops stopfile
```
Compared former two sorts of processing , this option will takes more time , due to it need to static the term frequency . The filter '2' is that the frequence less than 2 of terms will be reduced .