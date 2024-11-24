package com.ecwid.consul.v1.acl;

import java.util.List;

import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.acl.model.AclToken;
import com.ecwid.consul.v1.acl.model.NewAcl;
import com.ecwid.consul.v1.acl.model.UpdateAcl;

/**
 * Methods for the ACL Token HTTP API
 * 
 * @author Jon Huang (jon5477)
 *
 */
public interface AclClient {
	Response<AclToken> aclCreate(char[] token, NewAcl newAcl);

	Response<AclToken> aclRead(char[] token, String accessorId);

	Response<AclToken> aclReadSelf(char[] token);

	Response<AclToken> aclUpdate(char[] token, UpdateAcl updateAcl, String accessorId);

	Response<AclToken> aclClone(char[] token, String accessorId);

	Response<Void> aclDelete(char[] token, String accessorId);

	Response<List<AclToken>> aclList(char[] token, AclTokensRequest request);
}