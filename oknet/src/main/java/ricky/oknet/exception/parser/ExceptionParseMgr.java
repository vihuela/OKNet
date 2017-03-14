package ricky.oknet.exception.parser;

import java.util.ArrayList;
import java.util.List;

public enum ExceptionParseMgr {
    Instance;

    private List<ExceptionParser> parsers = new ArrayList<>();

    ExceptionParseMgr() {

        resetParses();
        connectionParse();
    }

    private void resetParses() {
        parsers.add(new NetExceptionParser());
        parsers.add(new ServerExceptionParser());
        parsers.add(new InternalExceptionParser());
        parsers.add(new UnknowExceptionParser());//last item must be UnknowExceptionParser
    }

    private void connectionParse() {
        for (int i = 0; i < parsers.size(); i++) {

            final ExceptionParser item = parsers.get(i);

            if (i + 1 < parsers.size()) {
                item.setNextParser(parsers.get(i + 1));
            }
        }
    }

    public void parseException(Throwable e, ExceptionParser.IHandler iHandler) {

        parsers.get(0).handleException(e, iHandler);

    }

    public void addParse(ExceptionParser parser) {

        if (parser != null) {
            parsers.add(0, parser);//自定义异常优先
            connectionParse();
        }
    }

}
