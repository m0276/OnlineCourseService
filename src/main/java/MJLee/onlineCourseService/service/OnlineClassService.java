package MJLee.onlineCourseService.service;


import MJLee.onlineCourseService.dto.OnlineClassDto;
import jakarta.transaction.Transactional;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
@Service
public class OnlineClassService {
    @Value("${url}")
    String startUrl;

    @Value("${serviceKey}")
    String key;

    @Value("${dataType}")
    String data;

    CategoryInterestedService service;

    @Autowired
    public OnlineClassService(CategoryInterestedService service) {
        this.service = service;
    }

    public List<OnlineClassDto> findAll(){
        try {
            return makeAllList(new ArrayList<>());
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<OnlineClassDto> findByCategory(String category){
        try {
            return makeCategoryList(category,new ArrayList<>());
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<OnlineClassDto> findByName(String name){
        try {
            return makeNameList(name,new ArrayList<>());
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<OnlineClassDto> findByFee(String fee){
        try{
            List<OnlineClassDto> list = makeAllList(new ArrayList<>());
            List<OnlineClassDto> findFee = new ArrayList<>();
            for(OnlineClassDto dto : list){
                if(dto.getFee().equals("무료")) findFee.add(dto);
                else if(Integer.parseInt(dto.getFee().substring(0,dto.getFee().length()-1)) <= Integer.parseInt(fee)){
                    findFee.add(dto);
                }
            }

            return findFee;
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<OnlineClassDto> findBySpendWeek(String week){
        try{
            List<OnlineClassDto> list = makeAllList(new ArrayList<>());
            List<OnlineClassDto> findWeek = new ArrayList<>();
            for(OnlineClassDto dto : list){
                if(Integer.parseInt(dto.getNeedWeek()) <= Integer.parseInt(week)){
                    findWeek.add(dto);
                }
            }

            return findWeek;
        }catch (Exception e){
            e.printStackTrace();
        }

        return new ArrayList<>();

    }

    //로그인 시 개인화 또는 인기도로 추천
    public List<OnlineClassDto> findAllWithSort(String userName) {
        //https://velog.io/@ie8907/%EC%B6%94%EC%B2%9C-%EC%8B%9C%EC%8A%A4%ED%85%9C-Recommender-System 참고하여 구현해보기
        try{
           List<OnlineClassDto> all = makeAllList(new ArrayList<>());
           List<OnlineClassDto> interested = service.findByUserName(userName);
           if(interested == null){
               interested = new ArrayList<>();
               for(OnlineClassDto dto : all){
                   if(dto.getPopular()){
                       interested.add(dto);
                   }
               }
           }
            all.removeAll(interested);

            interested.addAll(all);
            return interested;
        } catch (Exception e){
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    private List<OnlineClassDto> makeAllList(List<OnlineClassDto> list){
        try{
            StringBuilder urlStr = new StringBuilder(startUrl).append("/")
                    .append(key).append("/").append(data).append("/")
                    .append("OnlineCoures").append("/")
                    .append("1").append("/").append("1000");
            return makeList(urlStr,list);

        } catch (Exception e){
            e.printStackTrace();
        }

        return list;
    }

    private List<OnlineClassDto> makeCategoryList(String category, ArrayList<OnlineClassDto> list) throws URISyntaxException, IOException {
        try{
            StringBuilder urlStr = new StringBuilder(startUrl).append("/")
                    .append(key).append("/").append(data).append("/")
                    .append("OnlineCoures").append("/")
                    .append("1").append("/").append("1000").append("/")
                    .append(category);
            return makeList(urlStr,list);

        } catch (Exception e){
            e.printStackTrace();
        }

        return list;
    }

    private List<OnlineClassDto> makeNameList(String name, List<OnlineClassDto> list) throws URISyntaxException, IOException {
        try{
            StringBuilder urlStr = new StringBuilder(startUrl).append("/")
                    .append(key).append("/").append(data).append("/")
                    .append("OnlineCoures").append("/")
                    .append("1").append("/").append("1000").append("/").append(URLEncoder.encode(" ", StandardCharsets.UTF_8))
                    .append("/").append(name);
            return makeList(urlStr,list);


        } catch (Exception e){
            e.printStackTrace();
        }

        return list;
    }

    private List<OnlineClassDto> makeList(StringBuilder urlStr,List<OnlineClassDto> list){
        try{
            URL url = (new URI(urlStr.toString())).toURL();

            JSONObject jsonObject = new JSONObject(readStreamToString(getNetworkConnection((HttpURLConnection) url.openConnection())))
                    .getJSONObject("OnlineCoures");

            JSONArray jsonArray = jsonObject.getJSONArray("row");

            for(int i = 0 ; i < jsonArray.length() ; i++){
                OnlineClassDto classDto = new OnlineClassDto();
                JSONObject object = jsonArray.getJSONObject(i);

                classDto.setCategory(object.getString("CATEGORY_NM2"));
                classDto.setGetClassDate(object.getString("COURSE_REQUEST_DT"));
                classDto.setFee(object.getString("FEE"));
                classDto.setPopular(object.getString("POPULARITY_YN").equals("Y"));
                classDto.setCourseId(object.getString("COURSE_ID"));
                classDto.setAspId("ASP_ID");
                classDto.setNeedWeek(object.getString("WEEK_USE_YN").equals("Y") ? object.getString("WEEK_MK_CNT") : "0");
                classDto.setAspId(object.getString("ASP_ID"));
                classDto.setClassNumber(object.getString("CLASS_NO"));
                classDto.setCourseGubun(object.getString("COURSE_GUBUN"));
                classDto.setCourseName(object.getString("COURSE_NM"));

                list.add(classDto);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }


    /* URLConnection 을 전달받아 연결정보 설정 후 연결, 연결 후 수신한 InputStream 반환 */
    private InputStream getNetworkConnection(HttpURLConnection urlConnection) throws IOException {
        urlConnection.setConnectTimeout(3000);
        urlConnection.setReadTimeout(3000);
        urlConnection.setRequestMethod("GET");
        urlConnection.setDoInput(true);

        if(urlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new IOException("HTTP error code : " + urlConnection.getResponseCode());
        }

        return urlConnection.getInputStream();
    }

    /* InputStream을 전달받아 문자열로 변환 후 반환 */
    private String readStreamToString(InputStream stream) throws IOException{
        StringBuilder result = new StringBuilder();

        BufferedReader br = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));

        String readLine;
        while((readLine = br.readLine()) != null) {
            result.append(readLine).append("\n\r");
        }

        br.close();

        return (result.toString());
    }



}
