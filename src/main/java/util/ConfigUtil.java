package util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

/**
 * Created by Ma Yi on 2017/4/27.
 */
public class ConfigUtil {
    public static class GoldPage{
       public String pageName;
       public List<String> goldItems;
    }

    public static Logger log = LoggerFactory.getLogger(ConfigUtil.class);
    private static String udid;
    private static String port;
    private static ConfigUtil configUtil;
    private static Map<String,Object> configItems;
    private static List<GoldPage> configGoldPageListItems;
    private static String rootDir;
    private static String outputDir;
    private static String serverIp = "0.0.0.0";
    private static boolean showDomXML = false;
    private static boolean dbLogEnabled = false;
    private static boolean perLogEnabled = false;
    private static boolean generateVideo = true;
    private static boolean videoVertical = true;
    private static List<String> blackKeyList = new ArrayList<>();

    /**
     * 初始化在白名单只抓取一次的状态
     */
    private static List<String> caputure_onlyone_white_page = new ArrayList<>();

    /**
     * 初始化符合滚动xpath页面只抓取一次的状态
     */
    private static List<String> need_scrolled_xpath = new ArrayList<>();

    public static boolean isShowDomXML() {
        return showDomXML;
    }

    public static void setOutputDir (String dir){
        outputDir = dir;
    }

    //Android
    public static final String ANDROID_BOTTOM_TAB_BAR_ID = "ANDROID_BOTTOM_TAB_BAR_ID";
    public static final String ANDROID_EXCLUDE_TYPE = "ANDROID_EXCLUDE_TYPE";
    public static final String ANDROID_CLICK_XPATH_HEADER = "ANDROID_CLICK_XPATH_HEADER";

    public static final String IOS_EXCLUDE_BAR = "IOS_EXCLUDE_BAR";
    public static final String ANDROID_USERNAME = "ANDROID_USERNAME";
    public static final String ANDROID_PASSWORD = "ANDROID_PASSWORD";
    public static final String ANDROID_LOGIN_ELEMENTS = "LOGIN_ELEMENTS_ANDROID";
    public static final String ANDROID_BACK_KEY = "ANDROID_BACK_KEY";
    public static final String LOGIN_ELEMENTS_ANDROID = "LOGIN_ELEMENTS_ANDROID";
    public static final String BLACK_PAGES = "black_pages";

    public static final String GRAY_ACTIVITY_MAX_TRAVERSE = "gray_activity_max_traverse";//灰名页面遍历的最大次数（两个页面进入循环）
    public static final String GRAY_ACTIVITY_MAX_TRAVERSE_ITSELF = "gray_activity_max_traverse_itself";//灰名页面遍历自己遍历最大次数（单个页面进入死循环了）

    public static final String CAN_SCROLL_PAGE_XPATH="can_scrolled_page_xpath";



    //GENERAL CONFIG ITEM
    public static final String MAX_DEPTH = "MAX_DEPTH";
    public static final String CRASH_PIC_COUNT = "CRASH_PIC_COUNT";
    public static final String SCREEN_SHOT = "screenshot";
    public static final String IGNORE_CRASH = "IGNORE_CRASH";
    public static final String ENABLE_VERTICAL_SWIPE = "ENABLE_VERTICAL_SWIPE";
    public static final String DOM_DISPLAY = "DOM_DISPLAY" ;
    public static final String REMOVE_BOTTOM_BOUND = "REMOVE_BOTTOM_BOUND";
    public static final String GENERATE_VIDEO = "GENERATE_VIDEO";
    public static final String VIDEO_VERTICAL = "VIDEO_VERTICAL";
    public static final String USER_LOGIN_INTERVVAL = "USER_LOGIN_INTERVVAL";
    public static final String APPIUM_SERVER_IP = "APPIUM_SERVER_IP";

    //INFLUXDB
    public static final String DB_PORT = "DB_PORT";
    public static final String DB_IP = "DB_IP";

    //DEFAULT VALUE ITEM
    public static final String CRAWLER_RUNNING_TIME = "CRAWLER_RUNNING_TIME";

    //LIST
    public static final String ANDROID_VALID_PACKAGE_LIST = "ANDROID_VALID_PACKAGE_LIST";
    public static final String IOS_VALID_BUNDLE_LIST = "IOS_VALID_BUNDLE_LIST";
    public static final String ITEM_BLACKLIST = "ITEM_BLACKLIST";
    public static final String ITEM_WHITE_LIST = "ITEM_WHITE_LIST";
    public static final String NODE_NAME_EXCLUDE_LIST = "NODE_NAME_EXCLUDE_LIST";
    public static final String STRUCTURE_NODE_NAME_EXCLUDE_LIST = "STRUCTURE_NODE_NAME_EXCLUDE_LIST";
    public static final String PRESS_BACK_KEY_PACKAGE_LIST = "PRESS_BACK_KEY_PACKAGE_LIST";
    public static final String XPATH = "XPATH";
    public static final String ACTION = "ACTION";
    public static final String VALUE = "VALUE";
    public static final String INPUT_CLASS_LIST = "INPUT_CLASS_LIST";
    public static final String INPUT_TEXT_LIST = "INPUT_TEXT_LIST";
    public static final String BACK_KEY_TRIGGER_LIST = "BACK_KEY_TRIGGER_LIST";
    public static final String GRAB_KEYS = "GRAB_KEYS";

    public static final String CAPUTURE_ONLYONE_ON_WHITEPAGE = "caputure_onlyone_on_whitePage";
    public static final String NEED_SCROLLED_PAGE_XPATH = "need_scrolled_page_xpath";

    //Monkey
    public static final String SWIPE_RATIO = "SWIPE_RATIO";
    public static final String CLICK_RATIO = "CLICK_RATIO";
    public static final String CLICK_ITEM_BY_XPATH_RATIO = "CLICK_ITEM_BY_XPATH_RATIO";
    public static final String CLICK_SPECIAL_POINT_RATIO = "CLICK_SPECIAL_POINT_RATIO";
    public static final String RESTART_APP_RATIO = "RESTART_APP_RATIO";
    public static final String HOME_KEY_RATIO = "HOME_KEY_RATIO";
    public static final String LONG_PRESS_RATIO = "LONG_PRESS_RATIO";
    public static final String DOUBLE_TAP_RATIO = "DOUBLE_TAP_RATIO";
    public static final String PINCH_RATIO = "PINCH_RATIO";
    public static final String UNPINCH_RATIO = "UNPINCH_RATIO";
    public static final String DRAG_RATIO = "DRAG_RATIO";
    public static final String BACK_KEY_RATIO = "BACK_KEY_RATIO";

    //LOG
    public static final String DB_LOG = "DB_LOG";
    public static final String PERF_LOG = "PERF_LOG";

    public static final String MONKEY_SPECIAL_POINT_LIST = "MONKEY_SPECIAL_POINT_LIST";
    public static final String LONG_PRESS_LIST = "LONG_PRESS_LIST";
    public static final String CLICK_ITEM_XPATH_LIST = "CLICK_ITEM_XPATH_LIST";

    public static final ArrayList<String> MONKEY_EVENT_RATION_LIST = new ArrayList<>(Arrays.asList(RESTART_APP_RATIO,CLICK_RATIO,SWIPE_RATIO,
            LONG_PRESS_RATIO,CLICK_SPECIAL_POINT_RATIO,HOME_KEY_RATIO,
            DOUBLE_TAP_RATIO,PINCH_RATIO,UNPINCH_RATIO,DRAG_RATIO,BACK_KEY_RATIO,CLICK_ITEM_BY_XPATH_RATIO));
    private static long clickCount;

    //MINI Programme
    public static final String MINI_PROGRAM_NAME = "MINI_PROGRAM_NAME";
    //public static final String MINI_PROGRAM_PROCESS = "MINI_PROGRAM_PROCESS";


    public static ConfigUtil initialize(String file, String udid){
        log.info("Method: initialize");
        setUdid(udid);

        configItems = new HashMap<>();

        try {
            log.info("Reading config file " + file);

            InputStream input = new FileInputStream(new File(file));
            Yaml yaml = new Yaml();
            configUtil = new ConfigUtil();

            Map<String, Object> map = yaml.load(input);

            //初始化的顺序很重要
            //1.先设通用的值 GENERAL  2.设默认值 DEFAULT_VALUE 3.根据serial值去覆盖默认的属性值 4.然后其它值
            List<String> keyList = new ArrayList(Arrays.asList("GENERAL","DEFAULT_VALUE","MONKEY","LIST","CRITICAL_ELEMENT","LOGIN_ELEMENTS","MONKEY_LIST","MINI_PROGRAM","LOG","INFLUXDB",udid));
            if(map.get(udid)!=null){
                keyList.add(udid);
            }

            for(String key : keyList){
                Map<String,Object> tempMap = (Map<String,Object>)map.get(key);
                if(tempMap != null){
                    for(String itemKey : tempMap.keySet()){
                        configItems.put(itemKey,tempMap.get(itemKey));
                    }
                }
            }
            List<String> goldKeyList = new ArrayList(Arrays.asList("GOLD_LIST"));
            for(String key : goldKeyList){
                List<Map<String,Object>> tempList = (List<Map<String,Object>>)map.get(key);
                if(tempList != null && tempList.size()>0){
                    for(Map<String,Object> item:tempList){
                        Map<String,Object> tempMap = (Map<String,Object>)item;
                        if(tempMap != null){
                            String goldName  = (String) tempMap.get("GOLD_PAGE");
                            List<String> goldItems = (List<String>) tempMap.get("GOLD_ITEMS");
                            GoldPage goldPage = new GoldPage();
                            goldPage.pageName = goldName;
                            goldPage.goldItems = goldItems;
                            if(configGoldPageListItems ==null){
                                configGoldPageListItems = new ArrayList<>();
                            }
                            configGoldPageListItems.add(goldPage);
                        }
                    }
                }
            }


            port = getStringValue("PORT");
            clickCount = getLongValue("MAX_CLICK_COUNT");
            dbLogEnabled = ConfigUtil.getBooleanValue(DB_LOG);
            perLogEnabled = ConfigUtil.getBooleanValue(PERF_LOG);
            showDomXML = ConfigUtil.getBooleanValue(DOM_DISPLAY,true);
            generateVideo = ConfigUtil.getBooleanValue(GENERATE_VIDEO,true);
            videoVertical = ConfigUtil.getBooleanValue(VIDEO_VERTICAL,true);

            if(outputDir == null) {
                rootDir = System.getProperty("user.dir");
            }else{
                rootDir = outputDir ;
            }

            rootDir = rootDir + File.separator+ "crawler_output" + File.separator + getDeviceName().replace(":","_") + "-" + Util.getCurrentTimeFormat();;

            //Create Root dir
            Util.createDirs(rootDir);

            serverIp = getStringValue("APPIUM_SERVER_IP");
            caputure_onlyone_white_page = getListValue(CAPUTURE_ONLYONE_ON_WHITEPAGE);
            need_scrolled_xpath = getListValue(NEED_SCROLLED_PAGE_XPATH);

            log.info("rootDir is " + rootDir);

        }catch (Exception e){
            log.error("!!!!!!Fail to read config file");
            e.printStackTrace();
        }

        return configUtil;
    }

    public static void setDbLogEnabled(boolean dbLogEnabled) {
        ConfigUtil.dbLogEnabled = dbLogEnabled;
    }
    public static long getRetryCount() {
        return getLongValue("RETRY_COUNT");
    }
    public static void setPerLogEnabled(boolean perLogEnabled) {
        ConfigUtil.perLogEnabled = perLogEnabled;
    }
    public static void setMaxDepth(String depth) {
        setLongValue(MAX_DEPTH,depth);
    }
    public static void setScreenshotCount(String count) { configItems.put("SCREENSHOT_COUNT",count);}

    public static List<String> getBlackKeyList(){
        return blackKeyList;
    }

    public static String getServerIp() {
        return serverIp;
    }

    public static void setServerIp(String ip) {
        serverIp = ip;
    }

    public static boolean isGenerateVideo() {
        return generateVideo;
    }

    public static boolean isVideoVertical() {
        return videoVertical;
    }

    public static boolean isDbLogEnabled() {
        return dbLogEnabled;
    }

    public static boolean isPerLogEnabled() {
        return perLogEnabled;
    }

    public static boolean boundRemoved(){
        return getBooleanValue(REMOVE_BOTTOM_BOUND,true);
    }


    public static String getRootDir(){
        return rootDir;
    }

    public static String getScreenShotDir(){
        return rootDir + File.separator + SCREEN_SHOT + File.separator;
    }

    public static long getDefaultWaitSec() {
        return getLongValue("DEFAULT_WAIT_SEC");
    }

    public static long getDefaultPollingIntervalSec() {
        return getLongValue("DEFAULT_POLLING_INTERVAL_SEC");
    }

    public static String getPackageName() {
        return getStringValue("ANDROID_PACKAGE");
    }
    public static void setPackageName(String name) {
        setStringValue("ANDROID_PACKAGE",name);
    }




    public static long getClickCount() {
        return clickCount;
    }
    public static void setClickCount(long count) {
        clickCount = count;
    }

    public static long getScreenshotCount() { return getLongValue("SCREENSHOT_COUNT");}

    public static String getActivityName() {
        return getStringValue("ANDROID_MAIN_ACTIVITY");
    }
    public static void setActivityName(String activityName) {
        setStringValue("ANDROID_MAIN_ACTIVITY",activityName);
    }

    public static void setIgnoreCrash(boolean val) {
        setBooleanValue(IGNORE_CRASH,val);
    }

    public static long getDepth(){
        return getLongValue(MAX_DEPTH);
    }


    public static int getGrayActivityMaxTraberse(){
        return getIntValue(GRAY_ACTIVITY_MAX_TRAVERSE);
    }

    public static int getGrayActivityMaxTraberseItself(){
        return getIntValue(GRAY_ACTIVITY_MAX_TRAVERSE_ITSELF);
    }

    public static void setCrawlerRunningTime(String time){
         setLongValue(ConfigUtil.CRAWLER_RUNNING_TIME,time);
    }

    public static String getPort() {
        return port;
    }
    public static void setPort(String port) {
        ConfigUtil.port = port;
        setStringValue("PORT",port);
    }

    public static void setUdid(String name){
        udid = name;
    }
    public static String getUdid(){
        return udid;
    }

    public static String getDeviceName(){
        String deviceName = getStringValue("DEVICE_NAME");

        return deviceName ==  null ? udid : deviceName;
    }

    public static String getStringValue(String key) {
        String value = String.valueOf(configItems.get(key));
        log.info("Config : " + key + " = " + value);

        return value.equals("null") ? null : value.trim();
    }

    public static void setStringValue(String key,String value){
        configItems.put(key,value);
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<String> getListValue(String key) {
        ArrayList<String> list =(ArrayList) configItems.get(key);
        log.info("Config : " + key + " = " + list);

        return list == null? new ArrayList<>():list;
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<Map> getListMapValue(String key) {
        ArrayList<Map> list =(ArrayList) configItems.get(key);
        log.info("Config : " + key + " = " + list);

        return list == null? new ArrayList<>():list;
    }

    public static void setLongValue(String key, String val){
        configItems.put(key,Integer.parseInt(val));
    }

    public static long getLongValue(String key) {
        Integer value = getIntValue(key);
        return value == null? -100 : value.longValue();
    }

    public static int getIntValue(String key) {
        Integer value =(Integer) configItems.get(key);
        log.info("Config : " + key + " = " + value);

        return value == null? 0 : value;
    }

    public static void setBooleanValue(String key, boolean val){
        configItems.put(key,val);
    }

    public static boolean getBooleanValue(String key, boolean defaultValue) {
        Boolean value = (Boolean) configItems.get(key);

        log.info("Config : " + key + " = " + value);
        return value == null? defaultValue : value;
    }

    public static boolean getBooleanValue(String key) {
        return getBooleanValue(key,false);
    }

    public static List<String> getCaputureOnlyoneWhitePage() {
        return caputure_onlyone_white_page;
    }

    public static List<String> getNeedScrolledXpath() {
        return need_scrolled_xpath;
    }

    public static  List<GoldPage> getGoldConfigPageAndItsItems(){
        return configGoldPageListItems;
    }

    /**
     * 获取关键字 ，用来记录带有关键字页面并记录其路径
     * @return
     */
    public static List<String> getGrabKeys(){
        return getListValue(GRAB_KEYS);
    }
}
