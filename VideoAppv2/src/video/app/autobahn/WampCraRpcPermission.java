/******************************************************************************
 *
 *  Copyright 2012 Alejandro Hernandez
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 ******************************************************************************/

package video.app.autobahn;

public class WampCraRpcPermission{
    
    boolean call;
    String uri;
    
    public WampCraRpcPermission() {
    }
    
    public WampCraRpcPermission(boolean call, String uri) {
        this.call = call;
        this.uri = uri;
    }
    public boolean isCall() {
        return call;
    }
    public void setCall(boolean call) {
        this.call = call;
    }
    public String getUri() {
        return uri;
    }
    public void setUri(String uri) {
        this.uri = uri;
    }
    
    
    
}
