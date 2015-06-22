<%@ include file="/WEB-INF/jspx/Global.jsp" %>

<form:form method="POST" commandName="department" action="Departments.html">
	<table id="departmentsTable"><tr><td>		
		<table>	
			<tr>
				<td>		
					<spring:message code="department.label.name"/>
				</td>
			</tr>
		
			<tr>
				<td>
					<form:input path="name" style="width:300px" id="inputs"/>				
				</td>
			</tr>	
		</table>

		<table>
			<tr>
				<td>
					<input id="buttons" type="submit" name="save" style="width:98px" value='<spring:message code="department.saveButton"/>'>			
				</td>
				<td>
					<input id="buttons" type="submit" name="new" style="width:98px" <c:if test="${isNew}">disabled</c:if> value='<spring:message code="department.newButton"/>'>			
				</td>		
				<td>
					<input id="buttons" type="submit" name="delete" style="width:98px" <c:if test="${isNew}">disabled</c:if> value='<spring:message code="department.deleteButton"/>'>			
				</td>
			</tr>
			<tr>
				<td colspan="3">
					<font size=1 color="red">
						<form:errors path="name"/>
					</font>
				</td>
			</tr>				
			
		</table>
		<table>
			<tr>
				<td rowspan=3>
					<form:select multiple="true" path="id" items="${departments}" itemValue="id"  itemLabel="fullName" id="inputs" size = "15" style="width:299px" onClick="submit()" disabled="${departments == null}"/>
				</td>
				<td></td>	
				<td>
					<spring:message code="department.employeeLabel"/>
				</td>			
			</tr>
			<tr>
				<td></td>
				<td>
					<select multiple id="inputs" name="employers" size = "12" style="width:500px" <c:if test="${(employers == null || isNew)}">disabled</c:if>>
						<c:forEach items="${employers}" var="item">
							<option value='<c:out value="${item.id}"/>'>
								<c:out value="${item.name}"/>
								-
								<c:out value="${item.wage}"/>
							</option>
						</c:forEach>
					</select>	 
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
					<input id="buttons" type="submit" name="employee" value='<spring:message code="department.editValuesButton"/>' <c:if test="${(departments == null)}">disabled</c:if>>
				</td>
			</tr>	
		</table>
	</td></tr></table>	
</form:form>