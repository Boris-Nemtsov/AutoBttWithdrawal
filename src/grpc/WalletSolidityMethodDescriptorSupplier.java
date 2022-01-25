package grpc;

import org.tron.api.GrpcAPI;

import com.google.protobuf.Descriptors;

public final class WalletSolidityMethodDescriptorSupplier extends WalletSolidityBaseDescriptorSupplier implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
	private final String methodName;

	public WalletSolidityMethodDescriptorSupplier(String methodName) {
		this.methodName = methodName;
	}

    @Override
    public Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
}

abstract class WalletSolidityBaseDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
	WalletSolidityBaseDescriptorSupplier() {}

	@Override
	public Descriptors.FileDescriptor getFileDescriptor() {
		return GrpcAPI.getDescriptor();
	}
	
	@Override
	public Descriptors.ServiceDescriptor getServiceDescriptor() {
		return getFileDescriptor().findServiceByName("WalletSolidity");
	}
}