package org.openmrs.module.openhmis.inventory.api;

import org.openmrs.User;
import org.openmrs.annotation.Authorized;
import org.openmrs.module.openhmis.commons.api.PagingInfo;
import org.openmrs.module.openhmis.commons.api.entity.IObjectDataService;
import org.openmrs.module.openhmis.inventory.api.model.StockRoom;
import org.openmrs.module.openhmis.inventory.api.model.StockRoomTransaction;
import org.openmrs.module.openhmis.inventory.api.search.StockRoomTransactionSearch;
import org.openmrs.module.openhmis.inventory.api.util.PrivilegeConstants;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IStockRoomTransactionDataService extends IObjectDataService<StockRoomTransaction> {
	/**
	 * Returns the {@link StockRoomTransaction} with the specified transaction number.
	 * @param transactionNumber The transaction number.
	 * @return The {@link StockRoomTransaction} or {@code null} if there is no stock room with the specified
	 * transaction number.
	 * @should return null if transaction number not found
	 * @should return transaction with specified transaction number
	 * @should throw IllegalArgumentException if transaction number is null
	 * @should throw IllegalArgumentException if transaction number is empty
	 * @should throw IllegalArgumentException is transaction number is longer than 50 characters
	 */
	@Transactional(readOnly = true)
	@Authorized( {PrivilegeConstants.VIEW_TRANSACTIONS})
	StockRoomTransaction getTransactionByNumber(String transactionNumber);

	/**
	 * Returns the {@link StockRoomTransaction}s for the specified {@link StockRoom}.
	 * @param stockRoom The {@link StockRoom} that the transactions occurred in.
	 * @param paging The paging information or {@code null} to return all results.
	 * @return The transactions for the specified stock room.
	 * @should return transactions for specified room
	 * @should return empty list when no transactions
	 * @should return paged transactions when paging is specified
	 * @should return all transactions when paging is null
	 * @should return transactions with any status
	 * @should throw NullPointerException when stock room is null
	 */
	@Transactional(readOnly = true)
	@Authorized( {PrivilegeConstants.VIEW_TRANSACTIONS})
	List<StockRoomTransaction> getTransactionsByRoom(StockRoom stockRoom, PagingInfo paging);

	/**
	 * Returns the {@link StockRoomTransaction}s that are pending and can be completed by the specified
	 * {@link User}.
	 * @param user The {@link User}.
	 * @param paging The paging information or {@code null} to return all results.
	 * @return The pending transactions for the specified user.
	 * @should return all pending transactions for specified user
	 * @should return pending transactions created by user
	 * @should return pending transactions with user as attribute type user
	 * @should return pending transactions with user role as attribute type role
	 * @should return pending transactions with user role as child role of attribute type role
	 * @should return pending transactions with user role as grandchild role of attribute type role
	 * @should not return transactions when user role not descendant of attribute type role
	 * @should not return transactions when user role is parent of attribute type role
	 * @should not return any completed or cancelled transactions
	 * @should return empty list when no transactions
	 * @should return paged transactions when paging is specified
	 * @should return all transactions when paging is null
	 * @should throw NullPointerException when user is null
	 */
	@Transactional(readOnly = true)
	@Authorized( {PrivilegeConstants.VIEW_TRANSACTIONS})
	List<StockRoomTransaction> getUserPendingTransactions(User user, PagingInfo paging);

	/**
	 * Finds all {@link StockRoomTransaction}s using the specified {@link StockRoomTransactionSearch} settings.
	 * @param transactionSearch The transaction search settings.
	 * @return The transactions found or an empty list if no transactions were found.
	 */
	@Transactional(readOnly = true)
	@Authorized( {PrivilegeConstants.VIEW_TRANSACTIONS})
	List<StockRoomTransaction> findTransactions(StockRoomTransactionSearch transactionSearch);

	/**
	 * Finds all {@link StockRoomTransaction}s using the specified {@link StockRoomTransactionSearch} settings.
	 * @param transactionSearch The transaction search settings.
	 * @param paging The paging information.
	 * @return The transactions found or an empty list if no transactions were found.
	 * @should throw NullPointerException if transaction search is null
	 * @should throw NullPointerException if transaction search template object is null
	 * @should return an empty list if no transaction are found via the search
	 * @should return items filtered by transaction number
	 * @should return items filtered by transaction status
	 * @should return items filtered by transaction type
	 * @should return items filtered by source stock room
	 * @should return items filtered by destination stock room
	 * @should return items filtered by import transaction
	 * @should return items filtered by creation date
	 * @should return all items if paging is null
	 * @should return paged items if paging is specified
	 */
	@Transactional(readOnly = true)
	@Authorized( {PrivilegeConstants.VIEW_TRANSACTIONS})
	List<StockRoomTransaction> findTransactions(StockRoomTransactionSearch transactionSearch, PagingInfo paging);
}

