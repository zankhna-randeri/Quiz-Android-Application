package sjsu.zankhna.quizapp.utils;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

public class RequestPackage implements Parcelable {

    private String apiEndPoint;
    private String method = "GET";
    private Map<String, String> params = new HashMap<>();

    public String getApiEndPoint() {
        return apiEndPoint;
    }

    public void setApiEndPoint(String apiEndPoint) {
        this.apiEndPoint = apiEndPoint;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    protected RequestPackage(Parcel in) {
        this.apiEndPoint = in.readString();
        this.method = in.readString();
        int paramSize = in.readInt();
        this.params = new HashMap<String, String>(paramSize);
        while (paramSize >= 0) {
            String key = in.readString();
            String value = in.readString();
            this.params.put(key, value);
            paramSize--;
        }
    }

    public static final Creator<RequestPackage> CREATOR = new Creator<RequestPackage>() {
        @Override
        public RequestPackage createFromParcel(Parcel in) {
            return new RequestPackage(in);
        }

        @Override
        public RequestPackage[] newArray(int size) {
            return new RequestPackage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
