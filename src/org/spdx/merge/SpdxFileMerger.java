/* 
* Copyright (c) 2016 Gang Ling.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *   */

package org.spdx.merge;

import java.util.List;

import org.spdx.rdfparser.InvalidSPDXAnalysisException;
import org.spdx.rdfparser.model.SpdxDocument;
import org.spdx.rdfparser.model.SpdxFile;
import com.google.common.collect.Lists;

/**
 * Application to merge SPDX files information into one list. 
 * @author Gang
 *
 */

public class SpdxFileMerger {

	private SpdxDocument master = null;
	private SpdxLicenseMapper mapper = null;
	
	public SpdxFileMerger(SpdxDocument master, SpdxLicenseMapper mapper ){
		this.master = master;
		this.mapper = mapper;
	}
	
	public SpdxFile[] FileMeger(SpdxDocument[] subDocs) throws InvalidSPDXAnalysisException{
		
		List <SpdxFile> fileInMaster = master.getDocumentContainer().findAllFiles();
		List <SpdxFile> clonedMasterFileList = Lists.newArrayList(cloneFileList(fileInMaster));
		
		
		
		
		return null;	
	}
	
	
	public List <SpdxFile> cloneFileList(List <SpdxFile> oldList){
		List <SpdxFile> newList = Lists.newArrayList();
		for(SpdxFile temp : oldList){
			newList.add(temp);
		}
		return newList;
	}
	
}
