package zenghao.com.study.videoList.model;

public class VideoItem extends BaseItem {
    private String mVideoUrl;
    private String mCoverUrl;
 
    public VideoItem(String videoUrl, String coverUrl) {
        super(BaseItem.VIEW_TYPE_VIDEO); 
        mVideoUrl = videoUrl;
        mCoverUrl = coverUrl;
    } 
 
    public String getCoverUrl() {
        return mCoverUrl;
    } 
 
    public String getVideoUrl() {
        return mVideoUrl;
    } 
}