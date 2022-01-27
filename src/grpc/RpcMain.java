package grpc;

import org.tron.protos.Protocol.Account;

import com.google.protobuf.ByteString;

import io.grpc.CallOptions;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.MethodDescriptor;
import io.grpc.stub.ClientCalls;
import resources.Strings;

public class RpcMain {
	private static int CurrentAddressNumber = -1;
	private static ManagedChannel Builder;
	private static Account RequestAccount;
	
	static {
		changeAddress();
		RequestAccount = Account.newBuilder()
				.setAddress(ByteString.copyFrom(Utils.decodeBase58(Strings.FOUNDATION_WALLET_ID)))
				.build();
	}
	
	public static String getWalletInfo() {
		try
		{
			return ClientCalls.blockingUnaryCall(Builder, getGetAccountMethod(), CallOptions.DEFAULT, RequestAccount).toString();
		} catch (Exception e) {
			changeAddress();
			return "CHANGE_ADDRESS";
		}
	}
	
	private static void changeAddress() {
		try {
			if (Builder != null) {
				Builder.shutdownNow();
			}
		} catch (Exception e) {
		}
		
		CurrentAddressNumber = ++CurrentAddressNumber % Strings.FOUNDATION_NODE_LIST.length;
		Builder = ManagedChannelBuilder.forAddress(Strings.FOUNDATION_NODE_LIST[CurrentAddressNumber], 50051)
			.usePlaintext()
			.build();
	}
	
	private static MethodDescriptor<Account, Account> getGetAccountMethod() {
	    MethodDescriptor<Account, Account> getGetAccountMethod;
	    
	    getGetAccountMethod = MethodDescriptor.<Account, Account>newBuilder()
	              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
	              .setFullMethodName(MethodDescriptor.generateFullMethodName("protocol.Wallet", "GetAccount"))
	              .setSampledToLocalTracing(true)
	              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(Account.getDefaultInstance()))
	              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(Account.getDefaultInstance()))
                  .setSchemaDescriptor(new grpc.WalletSolidityMethodDescriptorSupplier("GetAccount"))
                  .build();
	    
	    return getGetAccountMethod;
	}
	
}
