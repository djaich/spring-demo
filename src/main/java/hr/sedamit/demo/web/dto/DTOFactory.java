package hr.sedamit.demo.web.dto;

import hr.sedamit.demo.model.*;

public class DTOFactory {

    public static UserDTO toUserDTO(User user) {
        if (user == null) {
            return null;
        }

        return new UserDTO(
                user.getId(),
                user.getUserName(),
                user.getFullName(),
                user.getAge(),
                user.isActive()
        );
    }

    public static AuthorDTO toAuthorDTO(Author author) {
        if (author == null) {
            return null;
        }

        return new AuthorDTO(
                author.getId(),
                author.getFirstName(),
                author.getLastName(),
                author.getYearOfBirth()
        );
    }

    public static BookDTO toBookDTO(Book book) {
        if (book == null) {
            return null;
        }

        return new BookDTO(
                book.getId(),
                book.getTitle(),
                DTOFactory.toAuthorDTO(book.getAuthor())
        );
    }

    public static AddressDTO toAddressDTO(Address address) {
        if (address == null) {
            return null;
        }

        return new AddressDTO(
                address.getCountry(),
                address.getCity(),
                address.getStreet(),
                address.getStreetNumber()
        );
    }

    public static MemberDTO toMemberDTO(Member member) {
        if (member == null) {
            return null;
        }

        return new MemberDTO(
                member.getId(),
                member.getUserName(),
                member.getFullName(),
                member.getAge(),
                member.isActive(),
                member.getMemberId(),
                toAddressDTO(member.getAddress())
        );
    }

}
