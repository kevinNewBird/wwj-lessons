package com.test.resourcepath;

import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***********************
 * Description: TODO <BR>
 * author: zhao.song
 * date: 2020/12/29 16:47
 * version: 1.0
 ***********************/
public class ClassResourePathTest {

    @Autowired
    private Environment environment;

    private void test() {
        environment.getProperty("sfsaf");
    }

    public static void main(String[] args) throws IOException {
        xxx();
    }

    public static final String PLACEHOLDER_REGEX = "\\$\\{([^\\{\\}]+)\\}";

    static Map<String,String> map = new HashMap<String,String>(){
        {
            put("${wcm.ip}", "172.16.25.100");
        }
    };

    public static void xxx() throws IOException {

        String profile = System.getProperty("spring.profiles.active");
        if (StringUtil.isBlank((profile))) {
            profile = "xjzmydev";
        }

        InputStream inStream = null;
        ByteArrayOutputStream swapStream = null;
        FileOutputStream fos = null;
        try {
            Properties p = new Properties();
            String profileFile = "trsids-agent-" + profile + ".properties";

            ClassPathResource inResource = new ClassPathResource(profileFile, ClassResourePathTest.class.getClassLoader());
            inStream = inResource.getInputStream();

            ClassPathResource outResource = new ClassPathResource("trsids-agent.properties", ClassResourePathTest.class.getClassLoader());
//            InputStream tempStream = outResource.getInputStream();
            fos = new FileOutputStream(outResource.getURL().getPath());
            int ch;
//            swapStream = new ByteArrayOutputStream();
            while ((ch = inStream.read()) != -1){
                fos.write(ch);
            }

            p.load(inStream);
            p.store(fos, "Configuration from " + profileFile);
            for (Object o : p.keySet()) {
                String m = m(p.get(o).toString());
                p.put(o, m);
            }
            System.out.println(p);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inStream != null) {
                inStream.close();
                inStream = null;
            }
            if (fos != null) {
                fos.flush();
                fos.close();
                fos = null;
            }
        }
    }

    public static String m(String valueWithPlaceholder) {
        Matcher matcher = Pattern.compile(PLACEHOLDER_REGEX).matcher(valueWithPlaceholder);
        String value = valueWithPlaceholder;
        while (matcher.find()) {
            String keyName = matcher.group();
            String placeHolder = keyName;
            value= valueWithPlaceholder.replace(placeHolder, map.get(keyName));
        }
        return value;
    }
}
