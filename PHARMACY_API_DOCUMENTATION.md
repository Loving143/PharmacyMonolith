# Pharmacy Application - Complete API Documentation

## Overview
This is a comprehensive Spring Boot-based pharmacy management system with complete end-to-end functionality including user management, medicine catalog, cart, orders, payments, notifications, and admin features.

## Base URL
```
http://localhost:8080
```

## Authentication
All APIs use JWT-based authentication. Include the JWT token in the Authorization header:
```
Authorization: Bearer <jwt_token>
```

---

## 1. AUTHENTICATION & ACCOUNTS APIs

### Login
```http
POST /auth/login
Content-Type: application/json

{
  "userName": "user@example.com",
  "password": "password123"
}
```

### Register
```http
POST /auth/signup
Content-Type: application/json

{
  "userName": "user@example.com",
  "password": "password123"
}
```

### Change Password
```http
POST /auth/changePassword
Content-Type: application/json

{
  "userName": "user@example.com",
  "oldPassword": "oldpass123",
  "newPassword": "newpass123"
}
```

---

## 2. USER PROFILE APIs

### Create User Profile
```http
POST /api/user-profiles/create
Authorization: Bearer <token>
Content-Type: application/json

{
  "firstName": "John",
  "lastName": "Doe",
  "phoneNumber": "+1234567890",
  "bloodGroup": "O+",
  "age": 30
}
```

### Get User Profile
```http
GET /api/user-profiles/profile/{userName}
Authorization: Bearer <token>
```

### Update User Profile
```http
PUT /api/user-profiles/update/{userName}
Authorization: Bearer <token>
Content-Type: application/json

{
  "firstName": "John",
  "lastName": "Smith",
  "phoneNumber": "+1234567890",
  "bloodGroup": "O+",
  "age": 31
}
```

---

## 3. ADDRESS MANAGEMENT APIs

### Add Address
```http
POST /address/add
Content-Type: application/json

{
  "name": "John Doe",
  "mobile": "+1234567890",
  "house": "123",
  "area": "Main Street",
  "landmark": "Near Park",
  "city": "New York",
  "state": "NY",
  "pincode": "10001",
  "latitude": 40.7128,
  "longitude": -74.0060,
  "isDefault": true
}
```

### Get All Addresses
```http
GET /address/list
```

### Update Address
```http
PUT /address/update/{id}
Content-Type: application/json

{
  "name": "John Doe",
  "mobile": "+1234567890",
  "house": "456",
  "area": "Second Street",
  "city": "New York",
  "state": "NY",
  "pincode": "10002"
}
```

### Set Default Address
```http
PUT /address/makeDefault/{id}
```

---

## 4. MEDICINE CATALOG APIs

### Add Medicine (Admin)
```http
POST /medicine/addMedicine
Content-Type: application/json

{
  "name": "Paracetamol",
  "brandName": "Crocin",
  "medicineCode": "MED001",
  "manufacturer": "GSK",
  "composition": "Paracetamol 500mg",
  "description": "Pain reliever and fever reducer",
  "stripQuantity": 100,
  "purchaseCostStrip": 10.0,
  "sellingCostStrip": 15.0,
  "manufactureDate": "2024-01-01",
  "expiryDate": "2026-01-01",
  "batchNumber": "BATCH001",
  "supplierName": "MedSupply Co",
  "supplierInvoiceNo": "INV001"
}
```

### Search Medicines
```http
GET /medicine/search/keyword?query=paracetamol
```

### Get Medicine by Code
```http
GET /medicine/fetchMedicine/{medicineCode}
```

### Get Medicines by Category
```http
GET /medicine/user/fetchMedicine/{subCategoryCode}
```

### Get All Categories
```http
GET /medicine/user/categories
```

---

## 5. CART MANAGEMENT APIs

### Get Cart
```http
GET /api/cart/{userName}
Authorization: Bearer <token>
```

### Add to Cart
```http
POST /api/cart/add
Authorization: Bearer <token>
Content-Type: application/json

{
  "medicineId": 1,
  "quantity": 2,
  "unitType": "STRIP",
  "userName": "user@example.com"
}
```

### Update Cart Item
```http
PUT /api/cart/update/{cartItemId}?quantity=3
Authorization: Bearer <token>
```

### Remove from Cart
```http
DELETE /api/cart/remove/{cartItemId}
Authorization: Bearer <token>
```

### Clear Cart
```http
DELETE /api/cart/clear/{userName}
Authorization: Bearer <token>
```

### Get Cart Item Count
```http
GET /api/cart/count/{userName}
Authorization: Bearer <token>
```

---

## 6. ORDER MANAGEMENT APIs

### Create Order
```http
POST /api/orders/create
Authorization: Bearer <token>
Content-Type: application/json

{
  "userName": "user@example.com",
  "deliveryAddressId": 1,
  "paymentMethod": "CREDIT_CARD",
  "orderItems": [
    {
      "medicineId": 1,
      "quantity": 2,
      "unitType": "STRIP"
    }
  ],
  "notes": "Urgent delivery required"
}
```

### Create Order from Cart
```http
POST /api/orders/create-from-cart?userName=user@example.com&deliveryAddressId=1&paymentMethod=CREDIT_CARD
Authorization: Bearer <token>
```

### Get Order by ID
```http
GET /api/orders/{orderId}
Authorization: Bearer <token>
```

### Get User Orders
```http
GET /api/orders/user/{userName}?page=0&size=10
Authorization: Bearer <token>
```

### Get Orders by Status
```http
GET /api/orders/user/{userName}/status/PENDING
Authorization: Bearer <token>
```

### Cancel Order
```http
PUT /api/orders/{orderId}/cancel?reason=Changed mind
Authorization: Bearer <token>
```

---

## 7. PAYMENT APIs

### Initiate Payment
```http
POST /api/payments/initiate?orderId=1&paymentMethod=CREDIT_CARD
Authorization: Bearer <token>
```

### Process Payment (Webhook)
```http
POST /api/payments/process
Content-Type: application/json

{
  "paymentId": "PAY123456789",
  "transactionId": "TXN987654321",
  "status": "SUCCESS",
  "gatewayResponse": "Payment successful"
}
```

### Get Payment by Order
```http
GET /api/payments/order/{orderId}
Authorization: Bearer <token>
```

### Get User Payments
```http
GET /api/payments/user/{userName}
Authorization: Bearer <token>
```

---

## 8. WISHLIST APIs

### Get Wishlist
```http
GET /api/wishlist/{userName}
Authorization: Bearer <token>
```

### Add to Wishlist
```http
POST /api/wishlist/add?userName=user@example.com&medicineId=1
Authorization: Bearer <token>
```

### Remove from Wishlist
```http
DELETE /api/wishlist/remove?userName=user@example.com&medicineId=1
Authorization: Bearer <token>
```

### Check if in Wishlist
```http
GET /api/wishlist/check?userName=user@example.com&medicineId=1
Authorization: Bearer <token>
```

---

## 9. NOTIFICATION APIs

### Get User Notifications
```http
GET /api/notifications/user/{userName}?page=0&size=20
Authorization: Bearer <token>
```

### Get Unread Notifications
```http
GET /api/notifications/user/{userName}/unread
Authorization: Bearer <token>
```

### Get Unread Count
```http
GET /api/notifications/user/{userName}/unread-count
Authorization: Bearer <token>
```

### Mark as Read
```http
PUT /api/notifications/{notificationId}/mark-read
Authorization: Bearer <token>
```

### Mark All as Read
```http
PUT /api/notifications/user/{userName}/mark-all-read
Authorization: Bearer <token>
```

---

## 10. PRESCRIPTION MANAGEMENT APIs

### Upload Prescription
```http
POST /api/user-profiles/prescription/upload
Authorization: Bearer <token>
Content-Type: multipart/form-data

files: [prescription1.pdf, prescription2.jpg]
userName: user@example.com
```

### Get User Prescriptions
```http
GET /api/user-profiles/prescription/{userName}
Authorization: Bearer <token>
```

### Download Prescription File
```http
GET /api/user-profiles/file/{fileId}/download
Authorization: Bearer <token>
```

---

## 11. ADMIN APIs

### Dashboard Stats
```http
GET /api/admin/dashboard/stats
Authorization: Bearer <admin_token>
```

### Get All Orders (Admin)
```http
GET /api/admin/orders?page=0&size=20
Authorization: Bearer <admin_token>
```

### Update Order Status (Admin)
```http
PUT /api/admin/orders/{orderId}/status?status=SHIPPED
Authorization: Bearer <admin_token>
```

### Add Tracking Number (Admin)
```http
PUT /api/admin/orders/{orderId}/tracking?trackingNumber=TRK123456789
Authorization: Bearer <admin_token>
```

### Process Refund (Admin)
```http
PUT /api/admin/payments/{paymentId}/refund
Authorization: Bearer <admin_token>
Content-Type: application/json

{
  "refundAmount": 100.0,
  "reason": "Product defective"
}
```

### Sales Report (Admin)
```http
GET /api/admin/reports/sales?startDate=2024-01-01&endDate=2024-12-31
Authorization: Bearer <admin_token>
```

### Inventory Report (Admin)
```http
GET /api/admin/reports/inventory
Authorization: Bearer <admin_token>
```

---

## 12. OTP & EMAIL APIs

### Generate OTP
```http
POST /otp/generate?username=user@example.com
```

### Validate OTP
```http
POST /otp/validate/otp?userName=user@example.com&otp=123456
```

### Send Email
```http
POST /email/send-otp
Content-Type: application/json

{
  "to": "user@example.com",
  "subject": "OTP Verification",
  "body": "Your OTP is: 123456",
  "templateData": {
    "name": "John Doe",
    "otp": "123456"
  }
}
```

---

## Response Formats

### Success Response
```json
{
  "status": "success",
  "data": { ... },
  "message": "Operation completed successfully"
}
```

### Error Response
```json
{
  "status": "error",
  "error": "Error description",
  "timestamp": "2024-01-10T10:30:00Z"
}
```

---

## Status Codes

- `200 OK` - Success
- `201 Created` - Resource created
- `400 Bad Request` - Invalid request
- `401 Unauthorized` - Authentication required
- `403 Forbidden` - Access denied
- `404 Not Found` - Resource not found
- `500 Internal Server Error` - Server error

---

## Order Status Flow
```
PENDING → CONFIRMED → PROCESSING → SHIPPED → OUT_FOR_DELIVERY → DELIVERED
                ↓
            CANCELLED
```

## Payment Status Flow
```
PENDING → PROCESSING → SUCCESS
            ↓
         FAILED
            ↓
        REFUNDED
```

---

## Key Features Implemented

✅ **Complete User Management**
- Registration, login, profile management
- Address management with geolocation
- Prescription upload and management

✅ **Comprehensive Medicine Catalog**
- Category and subcategory management
- Advanced search and filtering
- Inventory tracking with expiry management

✅ **Shopping Experience**
- Cart management with real-time updates
- Wishlist functionality
- Order creation from cart or direct

✅ **Order Management**
- Complete order lifecycle
- Status tracking with notifications
- Cancellation and returns

✅ **Payment Processing**
- Multiple payment methods
- Payment status tracking
- Refund management

✅ **Notification System**
- Real-time notifications
- Email and SMS integration
- Order and payment alerts

✅ **Admin Dashboard**
- Comprehensive analytics
- Order and payment management
- Inventory and user management
- Sales and inventory reports

✅ **Security & Authentication**
- JWT-based authentication
- Role-based access control
- OTP verification
- Audit logging

This API structure provides a complete, production-ready pharmacy management system with all essential features for both customers and administrators.