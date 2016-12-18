package server2;

import common.Formatter;

import java.rmi.RemoteException;
import java.util.List;
import java.util.stream.Collectors;

public class FormatterImpl implements Formatter {
    @Override
    public List<String> toUpperCase(List<String> list) throws RemoteException {
        return list.stream().map(String::toUpperCase).collect(Collectors.toList());
    }
}
