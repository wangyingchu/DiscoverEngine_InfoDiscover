package com.viewfnction.infoDiscoverEngine.util.exception;

public class InfoDiscoveryEngineException extends Exception{

    public void setCauseMessage(String message){
        Throwable throwable=new Throwable("[ "+ message + " ]");
        this.initCause(throwable);
    }

    public static InfoDiscoveryEngineRuntimeException getRuntimeException(String exceptionMessage){
        InfoDiscoveryEngineRuntimeException targetException=new InfoDiscoveryEngineRuntimeException();
        if(exceptionMessage!=null){
            targetException.setCauseMessage(exceptionMessage);
        }
        return targetException;
    }

    public static InfoDiscoveryEngineDataMartException getDataMartException(String exceptionMessage){
        InfoDiscoveryEngineDataMartException targetException=new InfoDiscoveryEngineDataMartException();
        if(exceptionMessage!=null){
            targetException.setCauseMessage(exceptionMessage);
        }
        return targetException;
    }

    public static InfoDiscoveryEngineInfoExploreException getInfoExploreException(String exceptionMessage){
        InfoDiscoveryEngineInfoExploreException targetException=new InfoDiscoveryEngineInfoExploreException();
        if(exceptionMessage!=null){
            targetException.setCauseMessage(exceptionMessage);
        }
        return targetException;
    }
}
