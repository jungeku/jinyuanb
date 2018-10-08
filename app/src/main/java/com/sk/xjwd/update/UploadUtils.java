package com.sk.xjwd.update;

/**
 * Created by liangpingyangyang on 2017/6/1.
 * 文件上传
 */

/*
public class UploadUtils {
    private static final String TAG = "uploadFile";
    private static final int TIME_OUT = 10 * 1000; // 超时时间
    private static final String CHARSET = "utf-8"; // 设置编码

    private static final String CROP_PATH = Environment.getExternalStorageDirectory() + File.separator+ "siyu/";

    public static void uploadImage(List<String> files) {
        if (files == null || files.size() == 0) {
            return;
        }
        int size = files.size();
        Map<String, File> hFiles = new HashMap<>();
        for (int i = 0; i < size; i++) {
            if (!"000000".equals(files.get(i))) {
                hFiles.put(String.valueOf(i), new File(files.get(i)));
            }
        }
        uploadImage(hFiles);
    }

    public static Map<String, File> getImageFile(List<String> files) {
        if (files == null || files.size() == 0) {
            return null;
        }
        int size = files.size();
        Map<String, File> hFiles = new HashMap<>();
        for (int i = 0; i < size; i++) {
            if (!"000000".equals(files.get(i))) {
                final String compressImage = ImageUtils.compressImage(files.get(i), CROP_PATH + File.separator + System.currentTimeMillis() + ".jpg",25);
                final File compressedPic = new File(compressImage);
//                File file = new File(files.get(i));
                hFiles.put(compressedPic.getName(), compressedPic);
            }
        }
        return hFiles;
    }

    */
/**
     * 上传头像
     *
     * @param userid   用户id
     * @param path     头像路径
     * @param callback 回调
     *//*

    public static void uploadUsrHead(String userid, String path, Callback callback) {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", userid);
        List<String> paths = new ArrayList();
        paths.add(path);
        uploadImg(params, "file", getImageFile(paths), callback, GlobalConstants.UPLOAD_URL);
    }

    */
/**
     * 反馈
     *
     * @param userid    用户id
     * @param content   反馈内容
     * @param filePaths 图片
     * @param callback  回调
     *//*

    public static void feedbackAction(String userid, String content, List<String> filePaths, Callback callback) {
        HashMap<String, String> params = new HashMap<>();
        params.put("customerId", userid);
        params.put("comment", content);
        uploadImg(params, "luoboimg", getImageFile(filePaths), callback, GlobalConstants.FEEDBACK_URL);
    }

    */
/**
     * 带参数上传图片
     *
     * @param params     参数
     * @param fileParams 图片流接收名称
     * @param files      文件map，包括文件名和文件
     * @param callback   回调
     * @param url        地址
     *//*

    public static void uploadImg(HashMap<String, String> params, String fileParams, Map<String, File> files, Callback callback, String url) {
        PostFormBuilder builder = OkHttpUtils.post().url(url);
        if (params != null) {
            Iterator entries = params.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry entry = (Map.Entry) entries.next();
                builder.addParams(entry.getKey().toString(), entry.getValue().toString());
            }
        }
        builder
                .files(fileParams, files)
                .build()
                .execute(callback);
    }


    */
/**
     * 上传图片
     *
     * @param files 图片列表
     *//*

    public static void uploadImage(Map<String, File> files) {
        OkHttpUtils
                .post()
                .url(GlobalConstants.UPLOAD_IMG)
                .files("luoboimg", files)
                .build()
                .execute(new Callback() {

                    @Override
                    public Object parseNetworkResponse(Response response, int id) throws Exception {
                        return null;
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("liangpingyy", "onError is " + e.toString());
                    }

                    @Override
                    public void onResponse(Object response, int id) {
                        Log.e("liangpingyy", "response is " + (response == null ? "" : response.toString()));
                    }
                });
    }

    public interface onUploadListener {
        void onUploadSuccess(Object response, int id);

        void onUploadFail(Call call, Exception e, int id);
    }
}
*/
