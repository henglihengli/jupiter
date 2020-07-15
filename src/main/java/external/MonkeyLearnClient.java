package external;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.monkeylearn.ExtraParam;
import com.monkeylearn.MonkeyLearn;
import com.monkeylearn.MonkeyLearnException;
import com.monkeylearn.MonkeyLearnResponse;

public class MonkeyLearnClient {
	private static final String API_KEY = "my_api_key";
	private static final String MODEL = "ex_YCya9nrn";

    public static void main(String[] args) {
		
		String[] textList = {
				"Elon Musk has shared a photo of the spacesuit designed by SpaceX. This is the second image shared of the new design and the first to feature the spacesuit’s full-body look.", 
				"Former Auburn University football coach Tommy Tuberville defeated ex-US Attorney General Jeff Sessions in Tuesday nights runoff for the Republican nomination for the U.S. Senate. ",
				"The NEOWISE comet has been delighting skygazers around the world this month – with photographers turning their lenses upward and capturing it above landmarks across the Northern Hemisphere."
				};
		List<List<String>> words = extractKeywords(textList);
		for (List<String> ws : words) {
			for (String w : ws) {
				System.out.println(w);
			}
			System.out.println();
		}
	}
	
	public static List<List<String>> extractKeywords(String[] text) {
		if (text == null || text.length == 0) {
			return new ArrayList<>();
		}
		
		// Use the API key from my account
		MonkeyLearn ml = new MonkeyLearn(API_KEY);
		
		//Use the API key from my account
		ExtraParam[] extraParams = {new ExtraParam("max_keywords", "3")};
		MonkeyLearnResponse response;
		
		try {
			response = ml.extractors.extract(MODEL, text, extraParams);
			JSONArray resultArray = response.arrayResult;
			return getKeywords(resultArray);
		} catch (MonkeyLearnException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}
	
	/* from JSONArray to List in Java */
	private static List<List<String>> getKeywords(JSONArray mlResultArray) {
		List<List<String>> topKeywords = new ArrayList<>();
		
		for (int i = 0; i < mlResultArray.size(); i++) {
			List<String> keywords = new ArrayList<>(); // output result
			JSONArray keywordsArray = (JSONArray) mlResultArray.get(i); // input keywords
			for (int j = 0; j < keywordsArray.size(); j++) {
				JSONObject keywordObject = (JSONObject) keywordsArray.get(j);
				String keyword = (String) keywordObject.get("keyword");
				keywords.add(keyword);
			}
			topKeywords.add(keywords);
		}
		
		return topKeywords;
	}
	
}
