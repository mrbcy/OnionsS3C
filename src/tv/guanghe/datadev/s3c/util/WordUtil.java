package tv.guanghe.datadev.s3c.util;

import java.io.IOException; 
import java.io.StringReader; 
import java.util.ArrayList; 
import java.util.Arrays;
import java.util.List; 

import org.wltea.analyzer.cfg.Configuration;
import org.wltea.analyzer.cfg.DefaultConfig;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;
/** 
 * 获取文章关键字 
 * @author anwj 
 * 
 */
public class WordUtil {
	private static String[] highLightColors = {"yellow","aqua","blueviolet",
			"chartreuse","crimson","gold","magenta","orange","red",
			"seagreen","steelblue","mediumslateblue"};
	/** 
     * 分词切分，并返回结链表 
     * @param keywords 
     * @return 
     */  
    private static List<Lexeme> doAnalyze(String keywords){ 
    	Configuration cfg = DefaultConfig.getInstance();
        System.out.println(cfg.getMainDictionary()); // 系统默认词库
        System.out.println(cfg.getQuantifierDicionary());
        List<Lexeme> lexemes = new ArrayList<Lexeme>();  
        IKSegmenter ikSeg = new IKSegmenter(new StringReader(keywords) , true); 
        try{  
            Lexeme l = null;  
            while( (l = ikSeg.next()) != null){  
                lexemes.add(l);  
            }  
        }catch(IOException e){  
            e.printStackTrace();  
        }  
        return lexemes;  
    }
    
    /** 
     * 根据分词结果生成SWMC搜索 
     * @param fieldName 
     * @param pathOption 
     * @param quickMode 
     * @return 
     */  
    private static List<String> getSWMC(List<Lexeme> lexemes){  
        //构造SWMC的查询表达式  
        StringBuffer keywordBuffer = new StringBuffer();  
        //精简的SWMC的查询表达式  
        StringBuffer keywordBuffer_Short = new StringBuffer();  
        //记录最后词元长度  
        int lastLexemeLength = 0;  
        //记录最后词元结束位置  
        int lastLexemeEnd = -1;  
          
        int shortCount = 0;  
        int totalCount = 0;  
        for(Lexeme l : lexemes){  
            totalCount += l.getLength();  
            //精简表达式  
            if(l.getLength() > 1){  
                keywordBuffer_Short.append(' ').append(l.getLexemeText());  
                shortCount += l.getLength();  
            }  
              
            if(lastLexemeLength == 0){  
                keywordBuffer.append(l.getLexemeText());                  
            }else if(lastLexemeLength == 1 && l.getLength() == 1  
                    && lastLexemeEnd == l.getBeginPosition()){//单字位置相邻，长度为一，合并)  
                keywordBuffer.append(l.getLexemeText());  
            }else{  
                keywordBuffer.append(' ').append(l.getLexemeText());  
                  
            }  
            lastLexemeLength = l.getLength();  
            lastLexemeEnd = l.getEndPosition();  
        }  
  

//       System.out.println(keywordBuffer.toString());
       return Arrays.asList(keywordBuffer.toString().split(" "));
    }
    
    public static List<String> getSegments(String sentence){
    	return getSWMC(doAnalyze(sentence));
    }
    
    public static String highLight(String text, List<String> segments){
    	for(int i = 0; i < segments.size(); i++){
    		text = text.replace(segments.get(i), "<span style=\"background-color:" + highLightColors[i % highLightColors.length] + "\">" + segments.get(i) + "</span>");
    	}
    	return text;
    	
    }
    
    public static void main(String[] args) {
    	List<String> segments = getSegments("活跃用户数量真人秀教师布置作业数据");
    	for(String segment : segments){
    		System.out.println(segment);
    	}
    	
    	String text = "<p>具体数据需求为：<br />输出20170601开始至今的所有作业的一下字段数据：<br />作业ID<br />布置时间<br />布置作业的教师ID<br />教师用户名<br />教师昵称<br />教师手机号<br />教师所在学校名（自己选的）<br />作业对应班级名称<br />班级中学生人数<br />作业包含知识点数<br />知识点完成人次（一个知识点一个人完成算一人次）<br />作业完成学生数（完成所有知识点算完成作业）<br />PS.该数据20170613和20170621各输出一次；</p>";
    	text = "活跃用户数量真人秀教师布置作业数据";
    	System.out.println(highLight(text,segments));
    }
}
