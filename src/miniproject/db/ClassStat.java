package miniproject.db;

public class ClassStat {
    private int classNameLength;
    private String className;
    private int methodNameLength;
    private String methodName;

    public int getClassNameLength() {
        return classNameLength;
    }

    public void setClassNameLength(int classNameLength) {
        this.classNameLength = classNameLength;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getMethodNameLength() {
        return methodNameLength;
    }

    public void setMethodNameLength(int methodNameLength) {
        this.methodNameLength = methodNameLength;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
