
export enum UserRole {
	ADMIN = "ADMIN",
	LIBRARIAN = "LIBRARIAN",
	MEMBER = "MEMBER",
	NONE = "NONE"
}

export enum UserAcStatus {
	ACTIVE ="ACTIVE",
	CLOSED = "CLOSED",
	REVOKED = "REVOKED",
	LOCKED = "LOCKED",
	INCOMPLETE = "INCOMPLETE"
}

export enum Gender {
	MALE = "M",
	FEMALE = "F",
	OTHER = "O"
}

export enum BookInstanceStatus {
	ISSUED = "ISSUED",
	AVAILABLE = "AVAILABLE",
	REMOVED = "REMOVED"
}

export class Country {
	id: number;
	name: string;
	isdCode: string;
	abbr: string;

	constructor(id?:number, name?: string, isdCode?: string, abbr?: string) {
		this.id = id;
		this.name = name;
		this.isdCode = isdCode;
		this.abbr = abbr;
	}
}


export class User {
	id: number;
}

export class Address {
	addrId: number;
	userId: number;
	line1: string;
	line2: string;
	city: string;
	state: string;
	pin: string;
	countryId: number;
	country: string;
}