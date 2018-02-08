# pustakalaya

"Pustakalya" is an online library management system.

This system has three user roles namely "admin", "librarian", "member" and "none".

The following are roles and their activities:

"none"
 - no priviledges

"member"
 - add/update profile picture
 - add/update mobile number
 - add/update address
 - initiate a request to withdraw membership


"librarian"
 - register/remove/revoke "member"
 - add/remove book
 - issue/collect book
A "librarian" is also a "member".


"admin"
 - register/remove/revoke "librarian"
An "admin" is also a "librarian".


This system has mainly two objects - user and book.
The following are properties of objects:

user
  - Email (also works as username)
  - Password
  - Name
  - Addresses
  - Mobile number
  - Photo
  - Date of birth
  - Role

book
  - Title
  - Authors
  - Publisher
  - Published year
  - ISBN
  - Edition
  - Number of pages
  - Price of book
