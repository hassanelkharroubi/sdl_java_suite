package com.smartdevicelink.test.rpc.responses;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.GetInteriorVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.ModuleData;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.rpc.GetInteriorVehicleDataResponse}
 */
public class GetInteriorVehicleDataResponseTests extends BaseRpcTests {

    @Override
    protected RPCMessage createMessage(){

        GetInteriorVehicleDataResponse msg = new GetInteriorVehicleDataResponse();

        msg.setIsSubscribed(Test.GENERAL_BOOLEAN);
        msg.setModuleData(Test.GENERAL_MODULEDATA);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_RESPONSE;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.GET_INTERIOR_VEHICLE_DATA.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(GetInteriorVehicleDataResponse.KEY_IS_SUBSCRIBED, Test.GENERAL_BOOLEAN);
            result.put(GetInteriorVehicleDataResponse.KEY_MODULE_DATA, JsonRPCMarshaller.serializeHashtable(Test.GENERAL_MODULEDATA.getStore()));
        }catch(JSONException e){
            fail(Test.JSON_FAIL);
        }

        return result;
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues () {
        // Test Values
        boolean testIsSubscribed = ( (GetInteriorVehicleDataResponse) msg ).getIsSubscribed();
        ModuleData testModuleData = ( (GetInteriorVehicleDataResponse) msg ).getModuleData();

        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, testIsSubscribed);
        assertTrue(Test.TRUE, Validator.validateModuleData(Test.GENERAL_MODULEDATA, testModuleData));

        // Invalid/Null Tests
        GetInteriorVehicleDataResponse msg = new GetInteriorVehicleDataResponse();
        assertNotNull(Test.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(Test.NULL, msg.getIsSubscribed());
        assertNull(Test.NULL, msg.getModuleData());
    }

    /**
     * Tests a valid JSON construction of this RPC message.
     */
    public void testJsonConstructor () {
        JSONObject commandJson = JsonFileReader.readId(this.mContext, getCommandType(), getMessageType());
        assertNotNull(Test.NOT_NULL, commandJson);

        try {
            Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
            GetInteriorVehicleDataResponse cmd = new GetInteriorVehicleDataResponse (hash);

            JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
            assertNotNull(Test.NOT_NULL, body);

            // Test everything in the json body.
            assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
            assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

            JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);

            ModuleData testModuleData = new ModuleData(JsonRPCMarshaller.deserializeJSONObject((JSONObject) JsonUtils.readObjectFromJsonObject(parameters, GetInteriorVehicleDataResponse.KEY_MODULE_DATA)));
            ModuleData cmdModuleData = cmd.getModuleData();

            assertTrue(Test.TRUE, Validator.validateModuleData(testModuleData, cmdModuleData) );
            assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(parameters, GetInteriorVehicleDataResponse.KEY_IS_SUBSCRIBED), cmd.getIsSubscribed());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
