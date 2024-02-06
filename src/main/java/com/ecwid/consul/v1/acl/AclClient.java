package com.ecwid.consul.v1.acl;

import java.util.List;

import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.acl.model.AclToken;
import com.ecwid.consul.v1.acl.model.NewAcl;
import com.ecwid.consul.v1.acl.model.UpdateAcl;

/**
 * @author Jon Huang (jon5477)
 */
public interface AclClient {
	Response<AclToken> aclCreate(String token, NewAcl newAcl);

	Response<AclToken> aclRead(String token, String accessorId);

	Response<AclToken> aclReadSelf(String token);

	Response<AclToken> aclUpdate(String token, UpdateAcl updateAcl, String accessorId);

	Response<AclToken> aclClone(String token, String accessorId);

	Response<Void> aclDelete(String token, String accessorId);

	Response<List<AclToken>> aclList(String token, AclTokensRequest request);
}