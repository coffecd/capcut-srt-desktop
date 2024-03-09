module com.ljl.com.capcutsrtdesktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.alibaba.fastjson2;
    requires lombok;


    opens com.ljl.com.capcutsrtdesktop to javafx.fxml;
    exports com.ljl.com.capcutsrtdesktop;
}